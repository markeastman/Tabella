package uk.me.eastmans.tabella.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import uk.me.eastmans.tabella.web.controller.HomeController;
import uk.me.eastmans.tabella.web.controller.IThymeleafController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/home"})
public class TabellaServlet extends HttpServlet
{
    private TemplateEngine templateEngine;
    private Map<String,IThymeleafController> controllersByURL;

    public TabellaServlet()
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
        final String path = getRequestPath(request);
        IThymeleafController controller = controllersByURL.get(path);
        if (controller == null)
        {
            response.setStatus(404);
        }
        else
        {
            try {
                controller.process(request, response, getServletContext(), templateEngine);
            }
            catch (Exception e)
            {
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
        controllersByURL.put("/home", new HomeController());
/*        controllersByURL.put("/product/list", new ProductListController());
        controllersByURL.put("/product/comments", new ProductCommentsController());
        controllersByURL.put("/order/list", new OrderListController());
        controllersByURL.put("/order/details", new OrderDetailsController());
        controllersByURL.put("/subscribe", new SubscribeController());
        controllersByURL.put("/userprofile", new UserProfileController());
*/
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