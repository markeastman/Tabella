package uk.me.eastmans.tabella.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import uk.me.eastmans.tabella.domain.BallotResult;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 * Created by meastman on 05/01/16.
 */
@Service
public class BallotAnswerNotificationServiceImpl implements BallotAnswerNotificationService {

    private Logger log = Logger.getLogger(getClass());

    // We will hold a map of the session to the id of the ballot they are watching,
    // Ideally we would want many sessions watching the same id
    private HashMap<WebSocketSession,Long> sessions = new HashMap<>();

    @Override
    public void registerWebSocketSession(WebSocketSession session) {
        // remember the session
        sessions.put(session, -1L);

    }

    @Override
    public void releaseWebSocketSession(WebSocketSession session, CloseStatus status) {
        // remove the session, we don't care what the reason is
        sessions.remove(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, TextMessage message) {
        // The text message should just contain the id of the ballot
        try {
            String payload = message.getPayload();
            Long id = Long.parseLong(payload);
            // now update the hash map
            sessions.put(session, id);
        }
        catch (Exception e)
        {
            // We cannot parse the message or other so try to remove the session
            sessions.remove(session.getId());
        }
    }

    @Override
    public void notifyBallotResult(BallotResult ballotResult, String data)
    {
        // Send this to all appropriate watchers
        // For now we can iterate the values in the hashmap but we need to store a reverse map
        // of ids to sessions.
        Iterator<Map.Entry<WebSocketSession,Long>> i = sessions.entrySet().iterator();
        while (i.hasNext())
        {
            Map.Entry<WebSocketSession,Long> entry = i.next();
            // does it match the ballot we have just updated
            if (entry.getValue() == ballotResult.getBallot().getId())
            {
                // We have a match, so send update to watcher
                WebSocketSession session = entry.getKey();
                try {
                    session.sendMessage( new TextMessage(data) );
                } catch (IOException e)
                {
                    // remove from current map
                    i.remove();
                }
            }
        }
    }
}
