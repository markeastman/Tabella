package uk.me.eastmans.tabella.web.controller;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import uk.me.eastmans.tabella.service.BallotService;
import uk.me.eastmans.tabella.util.StringHandling;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by meastman on 03/12/15.
 */
@ApplicationScoped
public class CreateBallotController implements IThymeleafController
{
    @Inject
    private BallotService ballotService;

    public void processGet(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext, final TemplateEngine templateEngine)
            throws Exception {

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("today", Calendar.getInstance());

        templateEngine.process("create", ctx, response.getWriter());
    }

    public void processPost(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext, final TemplateEngine templateEngine)
            throws Exception {
        // We now need to check that we are adding a new Ballot
        Map<String,List<String>> params = StringHandling.extractRequestParams(request);
        // So we have a question and some answers
        List<String> question = params.get( "question");
        List<String> answers = params.get( "answers" );
        // Some validation
        if (question != null && question.size() == 1 &&
                answers != null && answers.size() > 0)
        {
            ballotService.addBallot( question.get(0), answers );
        }
        response.sendRedirect("../home");
    }


}
