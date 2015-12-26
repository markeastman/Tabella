package uk.me.eastmans.tabella.bootstrap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import uk.me.eastmans.tabella.domain.Ballot;
import uk.me.eastmans.tabella.domain.Role;
import uk.me.eastmans.tabella.domain.User;
import uk.me.eastmans.tabella.repositories.BallotRepository;
import uk.me.eastmans.tabella.repositories.UserRepository;

import java.util.List;

/**
 * Created by meastman on 22/12/15.
 */
@Component
public class TabellaLoader implements ApplicationListener<ContextRefreshedEvent> {

    private BallotRepository ballotRepository;

    private UserRepository userRepository;

    private Logger log = Logger.getLogger(TabellaLoader.class);

    @Autowired
    public void setBallotRepository(BallotRepository ballotRepository) {
        this.ballotRepository = ballotRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {this.userRepository = userRepository;}

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Ballot b = new Ballot();
        b.setQuestion( "What is your favourite colour?");
        List<String> answers = b.getAnswers();
        answers.add( "red" );
        answers.add( "green" );
        answers.add( "blue" );

        ballotRepository.save(b);

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
    }
}
