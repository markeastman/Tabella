package uk.me.eastmans.tabella.core;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.enterprise.context.ApplicationScoped;
import java.io.Writer;

@ApplicationScoped
public class DisplayWriter {

    private TemplateEngine templateEngine = new TemplateEngine();

    public DisplayWriter()
    {
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setTemplateMode("HTML");
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateEngine.setTemplateResolver(templateResolver);
    }

    public void process(String templateName, IContext context, Writer writer)
    {
        templateEngine.process(templateName, context, writer);
    }
}
