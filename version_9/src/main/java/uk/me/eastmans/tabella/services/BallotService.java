package uk.me.eastmans.tabella.services;

import uk.me.eastmans.tabella.domain.Ballot;
import uk.me.eastmans.tabella.domain.User;

import java.util.List;

/**
 * Created by meastman on 22/12/15.
 */
public interface BallotService {
    Iterable<Ballot> listAllBallots();

    List<Ballot> getUnasweredBallotsForUser( User user );

    Ballot getBallotById(Long id);

    Ballot saveBallot(Ballot ballot);

    void deleteBallot(Long id);
}
