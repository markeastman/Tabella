package uk.me.eastmans.tabella.controllers;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import uk.me.eastmans.tabella.bootstrap.TabellaLoader;
import uk.me.eastmans.tabella.core.DisplayWriter;
import uk.me.eastmans.tabella.domain.User;
import uk.me.eastmans.tabella.services.BallotService;
import uk.me.eastmans.tabella.services.UserService;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.io.*;
import java.net.URI;
import java.util.Optional;

@Path("/")
public class HomeController {

    @javax.ws.rs.core.Context
    private HttpServletResponse httpServletResponse;

    @Inject
    private DisplayWriter displayWriter;

    @Inject
    private TabellaLoader tabellaLoader;

    @Inject
    private UserService userService;

    @Inject
    private BallotService ballotService;

    @GET
    @Path("/home")
    @Produces(MediaType.TEXT_HTML)
    public void home() throws IOException {

        tabellaLoader.populateDatabase();

        //User u = ((CurrentUser)authentication.getPrincipal()).getUser();
        Optional<User> u = userService.getUserByEmail("mark@eastmans.me.uk");
        User user = u.get();

        //model.addAttribute("ballots", ballotService.getUnasweredBallotsForUser(u));

        //long unanswered = ballotResultService.getBallotCountUnansweredByUser(u);
        //long userAnswered = ballotResultService.getBallotCountAnsweredByUser(u);
        long asked = ballotService.getBallotCountForUser(user);
        long answered = ballotService.getBallotCountAnsweredForUser(user);

        Context context = new Context();
        context.setVariable("ballots", null);
        context.setVariable("unanswered", "1");
        context.setVariable("userAnswered", "0");
        context.setVariable("asked", asked);
        context.setVariable("answered", answered);
        displayWriter.process("home", context, httpServletResponse.getWriter());
    }
}
