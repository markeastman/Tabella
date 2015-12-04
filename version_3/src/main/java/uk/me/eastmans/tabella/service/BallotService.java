package uk.me.eastmans.tabella.service;

import uk.me.eastmans.tabella.data.Ballot;

import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Model;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meastman on 03/12/15.
 */
@ApplicationScoped
@Named
public class BallotService
{
    public List<Ballot> getAllBallots()
    {
        // Build a list of all the ballots for now,
        // later we will get them from the database
        List<Ballot> ballots = new ArrayList<>();
        ballots.add( new Ballot( "Your favourite colour", "red", "green", "blue") );
        ballots.add( new Ballot( "Your favourite shape", "circle", "triangle", "square", "pentagram") );
        return ballots;
    }
}
