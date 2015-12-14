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
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by meastman on 03/12/15.
 */
@ApplicationScoped
public class UpdateBallotController implements IThymeleafController
{
    @Inject
    private BallotService ballotService;

    @Inject
    Logger log;

    public void processGet(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext, final TemplateEngine templateEngine)
            throws Exception {

    }

    public void processPost(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext, final TemplateEngine templateEngine)
            throws Exception {
        // We now need to check that we are adding a new Ballot
        Map<String,List<String>> params = StringHandling.extractRequestParams(request);
        // Get the id field
        List<String> idParam = params.get("id");
        if (idParam != null && idParam.size() == 1)
        {
            List<String> answerParam = params.get(idParam.get(0));
            if (answerParam != null && answerParam.size() == 1)
            {
                ballotService.answerBallot( idParam.get(0), answerParam.get(0) );
            }
        }
        log.info( "++++ " + params);
        response.sendRedirect("../home");
    }


}
