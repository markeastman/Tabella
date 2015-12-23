package uk.me.eastmans.tabella.services;

import uk.me.eastmans.tabella.domain.Ballot;

/**
 * Created by meastman on 22/12/15.
 */
public interface BallotService {
    Iterable<Ballot> listAllBallots();

    Iterable<Ballot> getAllOpenBallots();

    Ballot getBallotById(Long id);

    Ballot saveBallot(Ballot ballot);

    void deleteBallot(Long id);
}
