package uk.me.eastmans.tabella.configuration;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import uk.me.eastmans.tabella.controllers.WebSocketController;

/**
 * Created by meastman on 05/01/16.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private Logger log = Logger.getLogger(WebSocketConfiguration.class);

    private WebSocketController websocketController;

    @Autowired
    public void setWebSocketController(WebSocketController controller)
    {
        this.websocketController = controller;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler( websocketHandler(), "/websocketHandler" );
    }

    public WebSocketHandler websocketHandler() {
        return websocketController;
    }
}
