package uk.me.eastmans.tabella.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import uk.me.eastmans.tabella.services.BallotAnswerNotificationService;

import java.io.IOException;
import java.security.Principal;

/**
 * Created by meastman on 05/01/16.
 */
@Controller
public class WebSocketController extends TextWebSocketHandler {

    private Logger log = Logger.getLogger(WebSocketController.class);

    private BallotAnswerNotificationService notificationService;

    @Autowired
    public void setBallotAnswerNotificationService( BallotAnswerNotificationService service )
    {
        this.notificationService = service;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("connection Established for " + session.getId());
        notificationService.registerWebSocketSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("connection closed for " + session.getId() + " status " + status);
        notificationService.releaseWebSocketSession(session, status);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        notificationService.handleMessage(session, message);
    }
}
