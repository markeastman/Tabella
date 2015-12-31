package uk.me.eastmans.tabella.bootstrap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import uk.me.eastmans.tabella.domain.Ballot;
import uk.me.eastmans.tabella.domain.BallotResult;
import uk.me.eastmans.tabella.domain.Role;
import uk.me.eastmans.tabella.domain.User;
import uk.me.eastmans.tabella.repositories.BallotRepository;
import uk.me.eastmans.tabella.repositories.BallotResultRepository;
import uk.me.eastmans.tabella.repositories.UserRepository;

import java.util.List;
import java.util.Random;

/**
 * Created by meastman on 22/12/15.
 */
@Component
public class TabellaLoader implements ApplicationListener<ContextRefreshedEvent> {

    private BallotRepository ballotRepository;

    private BallotResultRepository ballotResultRepository;

    private UserRepository userRepository;

    private Logger log = Logger.getLogger(TabellaLoader.class);

    @Autowired
    public void setBallotRepository(BallotRepository ballotRepository) {
        this.ballotRepository = ballotRepository;
    }

    @Autowired
    public void setBallotResultRepository(BallotResultRepository ballotResultRepository) {
        this.ballotResultRepository = ballotResultRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {this.userRepository = userRepository;}

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        User admin = new User();
        admin.setEmail("mark@eastmans.me.uk");
        admin.setPasswordHash(new BCryptPasswordEncoder().encode("password"));
        admin.setRole(Role.ADMIN);

        userRepository.save(admin);

        User user = new User();
        user.setEmail("lynn@eastmans.me.uk");
        user.setPasswordHash(new BCryptPasswordEncoder().encode("password"));
        user.setRole(Role.USER);

        userRepository.save(user);

        Ballot b = new Ballot();
        b.setUser(user);
        b.setQuestion( "What is your favourite colour?");
        List<String> answers = b.getAnswers();
        answers.add( "red" );
        answers.add( "green" );
        answers.add( "blue" );

        ballotRepository.save(b);

        Ballot b2 = new Ballot();
        b2.setUser(user);
        b2.setQuestion( "What is your favourite shape?");
        List<String> answers2 = b2.getAnswers();
        answers2.add( "square" );
        answers2.add( "circle" );
        answers2.add( "triangle" );

        ballotRepository.save(b2);

        Ballot b3 = new Ballot();
        b3.setUser(admin);
        b3.setQuestion( "What is your favourite computer?");
        List<String> answers3 = b3.getAnswers();
        answers3.add( "laptop" );
        answers3.add( "tablet" );
        answers3.add( "phone" );

        ballotRepository.save(b3);

        Random r = new Random();
        for (int i = 0; i < 10; i++)
        {
            User u = new User();
            u.setEmail("u"+i+"@eastmans.me.uk");
            u.setPasswordHash(new BCryptPasswordEncoder().encode("password"));
            u.setRole(Role.USER);

            userRepository.save(u);

            BallotResult br = new BallotResult( u, b3, r.nextInt(3));
            ballotResultRepository.save(br);
        }
    }
}
