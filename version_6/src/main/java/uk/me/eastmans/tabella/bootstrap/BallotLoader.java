package uk.me.eastmans.tabella.bootstrap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import uk.me.eastmans.tabella.domain.Ballot;
import uk.me.eastmans.tabella.repositories.BallotRepository;

import java.util.List;

/**
 * Created by meastman on 22/12/15.
 */
@Component
public class BallotLoader implements ApplicationListener<ContextRefreshedEvent> {

    private BallotRepository ballotRepository;

    private Logger log = Logger.getLogger(BallotLoader.class);

    @Autowired
    public void setBallotRepository(BallotRepository ballotRepository) {
        this.ballotRepository = ballotRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Ballot b = new Ballot();
        b.setQuestion( "What is your favourite colour?");
        List<String> answers = b.getAnswers();
        answers.add( "red" );
        answers.add( "green" );
        answers.add( "blue" );

        ballotRepository.save(b);

    }
}
