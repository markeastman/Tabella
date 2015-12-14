package uk.me.eastmans.tabella.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import uk.me.eastmans.tabella.web.controller.CreateBallotController;
import uk.me.eastmans.tabella.web.controller.HomeController;
import uk.me.eastmans.tabella.web.controller.IThymeleafController;
import uk.me.eastmans.tabella.web.controller.UpdateBallotController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/home", "/create/*", "/ballot/*"})
public class TabellaServlet extends HttpServlet
{
    private TemplateEngine templateEngine;
    private Map<String,IThymeleafController> controllersByURL;

    @Inject
    private HomeController homeController;

    @Inject
    private CreateBallotController createBallotController;

    @Inject
    private UpdateBallotController updateBallotController;

    @PostConstruct
    public void initializeServlet()
    {
        // Initialise the thymeleaf engine and controllers
        initializeTemplateEngine();
        initializeControllersByURL();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        /*
         * Write the response headers
         */
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // Use the template engine to handle the request
        String path = getRequestPath(request);
        // Now we only want to handle the first path part to get controller name
        System.out.println( "Checking path " + path );
        IThymeleafController controller = controllersByURL.get(path);
        if (controller == null)
        {
            System.out.println( "WARNING: No such controller found " + path);
            response.setStatus(404);
        }
        else
        {
            try {
                controller.processGet(request, response, getServletContext(), templateEngine);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new ServletException(e);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        /*
         * Write the response headers
         */
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // Use the template engine to handle the request
        String path = getRequestPath(request);
        System.out.println( "Checking path " + path );
        int index = path.lastIndexOf('/');
        if (index > 0)
            path = path.substring(0, index);
        IThymeleafController controller = controllersByURL.get(path);
        if (controller == null)
        {
            System.out.println( "WARNING: No such controller found " + path);
            response.setStatus(404);
        }
        else
        {
            try {
                controller.processPost(request, response, getServletContext(), templateEngine);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new ServletException(e);
            }
        }
    }

    private void initializeTemplateEngine()
    {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();

        // XHTML is the default mode, but we will set it anyway for better understanding of code
        templateResolver.setTemplateMode("HTML5");
        // This will convert "home" to "/WEB-INF/templates/home.html"
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        // Set template cache TTL to 1 hour. If not set, entries would live in cache until expelled by LRU
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));

        // Cache is set to true by default. Set to false if you want templates to
        // be automatically updated when modified.
        templateResolver.setCacheable(true);

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    private void initializeControllersByURL()
    {
        controllersByURL = new HashMap<String, IThymeleafController>();
        controllersByURL.put("/home", homeController);
        controllersByURL.put("/create", createBallotController);
        controllersByURL.put("/ballot", updateBallotController);
    }

    private String getRequestPath(final HttpServletRequest request) {

        String requestURI = request.getRequestURI();
        final String contextPath = request.getContextPath();

        final int fragmentIndex = requestURI.indexOf(';');
        if (fragmentIndex != -1) {
            requestURI = requestURI.substring(0, fragmentIndex);
        }

        if (requestURI.startsWith(contextPath)) {
            return requestURI.substring(contextPath.length());
        }
        return requestURI;
    }

}