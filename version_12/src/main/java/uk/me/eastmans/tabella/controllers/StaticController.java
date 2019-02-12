package uk.me.eastmans.tabella.controllers;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

@Path("/")
public class StaticController {

//    @javax.ws.rs.core.Context
//    private ServletConfig servletConfig;
//    @javax.ws.rs.core.Context
//    private ServletContext servletContext;
//    @javax.ws.rs.core.Context
//    private HttpServletRequest httpServletRequest;
    @javax.ws.rs.core.Context
    private HttpServletResponse httpServletResponse;

    @javax.ws.rs.core.Context
    private UriInfo uriInfo;

    @GET
    @Path("/{var:.*}")
    @Produces(MediaType.TEXT_PLAIN)
    public void staticFiles() {
        String path = uriInfo.getPath();
        System.out.println( "Processing: " + path );

        // We just need t0 copy the file to the output area, will have to handle byte streams at some point
        // for images
        // Work out the mime type
        if (path.endsWith(".css"))
            httpServletResponse.setContentType("text/css");
        else if (path.endsWith(".jpg"))
            httpServletResponse.setContentType("image/jpg");
        else if (path.endsWith(".gif"))
            httpServletResponse.setContentType("image/gif");
        else if (path.endsWith(".png"))
            httpServletResponse.setContentType("image/png");
        else if (path.endsWith(".ico"))
            httpServletResponse.setContentType("image/x-icon");
        else if (path.endsWith(".js"))
            httpServletResponse.setContentType("text/javascript");
        else
            httpServletResponse.setContentType("application/octet-stream");
        byte[] buff = new byte[1024];
        Writer stringWriter = new StringWriter();
        FileInputStream fStream = null;

        try {
            fStream = new FileInputStream("static/"+path);
            int n;
            while ((n = fStream.read(buff)) != -1) {
                httpServletResponse.getOutputStream().write(buff, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stringWriter.close();
                fStream.close();
                httpServletResponse.getOutputStream().flush();
            } catch (Exception e2) {}
        }
    }
}
