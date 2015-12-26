package uk.me.eastmans.tabella.services;

import uk.me.eastmans.tabella.domain.BallotResult;

/**
 * Created by meastman on 22/12/15.
 */
public interface BallotResultService {
    Iterable<BallotResult> listAllBallotAnswers();

    BallotResult getBallotAnswerById(Long id);

    BallotResult saveBallotAnswer(BallotResult ballot);

    void deleteBallotAnswer(Long id);
}
