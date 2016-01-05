package uk.me.eastmans.tabella.services;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import uk.me.eastmans.tabella.domain.BallotResult;

/**
 * Created by meastman on 05/01/16.
 */
public interface BallotAnswerNotificationService {
    void registerWebSocketSession(WebSocketSession session);

    void releaseWebSocketSession(WebSocketSession session, CloseStatus status);

    void handleMessage(WebSocketSession session, TextMessage message);

    void notifyBallotResult(BallotResult ballotResult,String data);
}
