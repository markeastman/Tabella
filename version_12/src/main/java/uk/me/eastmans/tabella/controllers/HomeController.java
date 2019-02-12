package uk.me.eastmans.tabella.controllers;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import uk.me.eastmans.tabella.core.DisplayWriter;

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

@Path("/")
public class HomeController {

    @javax.ws.rs.core.Context
    private HttpServletResponse httpServletResponse;

    @Inject
    private DisplayWriter displayWriter;

    @GET
    @Path("/home")
    @Produces(MediaType.TEXT_HTML)
    public void home() throws IOException {

        Context context = new Context();
        context.setVariable("unanswered", "1");
        context.setVariable("userAnswered", "0");
        context.setVariable("asked", "2");
        context.setVariable("answered", "0");
        StringWriter stringWriter = new StringWriter();
        displayWriter.process("home", context, httpServletResponse.getWriter());
    }
}
