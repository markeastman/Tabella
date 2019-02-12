package uk.me.eastmans.tabella.bootstrap;

import org.jboss.shamrock.runtime.ShutdownEvent;
import org.jboss.shamrock.runtime.StartupEvent;
import uk.me.eastmans.tabella.domain.Ballot;
import uk.me.eastmans.tabella.domain.BallotResult;
import uk.me.eastmans.tabella.domain.Role;
import uk.me.eastmans.tabella.domain.User;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class TabellaLoader {
    @Inject
    private EntityManager em;

    private boolean dbPopulated = false;

    void onStart(@Observes StartupEvent ev) {
        System.out.println("The application is starting...");
    }

    void onStop(@Observes ShutdownEvent ev) {
        System.out.println("The application is stopping...");
    }

    @Transactional
    public void populateDatabase()
    {
        if (!dbPopulated) {
            dbPopulated = true;

            User admin = new User();
            admin.setEmail("mark@eastmans.me.uk");
            admin.setFullName("Mark Eastman");
            //admin.setPasswordHash(new BCryptPasswordEncoder().encode("password"));
            admin.setRole(Role.ADMIN);

            em.persist(admin);

            User user = new User();
            user.setEmail("lynn@eastmans.me.uk");
            user.setFullName("Lynn Eastman");
            //user.setPasswordHash(new BCryptPasswordEncoder().encode("password"));
            user.setRole(Role.USER);

            em.persist(user);


            Ballot b = new Ballot();
            b.setUser(user);
            b.setQuestion( "What is your favourite colour?");
            List<String> answers = b.getAnswers();
            answers.add( "red" );
            answers.add( "green" );
            answers.add( "blue" );

            em.persist(b);

            Ballot b2 = new Ballot();
            b2.setUser(user);
            b2.setQuestion( "What is your favourite shape?");
            List<String> answers2 = b2.getAnswers();
            answers2.add( "square" );
            answers2.add( "circle" );
            answers2.add( "triangle" );

            em.persist(b2);

            Ballot b3 = new Ballot();
            b3.setUser(admin);
            b3.setQuestion( "What is your favourite computer?");
            List<String> answers3 = b3.getAnswers();
            answers3.add( "laptop" );
            answers3.add( "tablet" );
            answers3.add( "phone" );

            em.persist(b3);

            Random r = new Random();
            for (int i = 0; i < 20; i++)
            {
                User u = new User();
                u.setEmail("u"+i+"@eastmans.me.uk");
                u.setFullName("U" + i + " Eastman");
                //u.setPasswordHash(new BCryptPasswordEncoder().encode("password"));
                u.setRole(Role.USER);

                em.persist(u);

                // Only provide answers for first 10
                if (i < 10) {
                    BallotResult br = new BallotResult(u, b3, r.nextInt(3));
                    br.setLatitude(r.nextFloat() * 80);
                    br.setLongitude(r.nextFloat() * 180 - 90);
                    em.persist(br);
                }
            }
        }
    }
}
