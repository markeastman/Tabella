package uk.me.eastmans.tabella.web.controller;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import uk.me.eastmans.tabella.service.BallotService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by meastman on 03/12/15.
 */
@ApplicationScoped
public class HomeController implements IThymeleafController
{
    @Inject
    private BallotService ballotService;

    public void processGet(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext, final TemplateEngine templateEngine)
            throws Exception {

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("ballots", ballotService.getAllOpenBallots());

        templateEngine.process("home", ctx, response.getWriter());
    }

    public void processPost(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext, final TemplateEngine templateEngine)
            throws Exception {

    }

}
