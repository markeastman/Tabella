package uk.me.eastmans.tabella.web.controller;

import org.thymeleaf.TemplateEngine;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by meastman on 03/12/15.
 */
public interface IThymeleafController {
    public void processGet(
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext, TemplateEngine templateEngine)
            throws Exception;
    public void processPost(
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext, TemplateEngine templateEngine)
            throws Exception;
}
