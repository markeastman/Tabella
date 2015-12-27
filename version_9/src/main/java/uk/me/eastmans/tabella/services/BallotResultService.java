package uk.me.eastmans.tabella.services;

import uk.me.eastmans.tabella.domain.BallotResult;
import uk.me.eastmans.tabella.domain.User;

/**
 * Created by meastman on 22/12/15.
 */
public interface BallotResultService {
    Iterable<BallotResult> listAllBallotAnswers();

    long getBallotCountUnansweredByUser(User user);

    long getBallotCountAnsweredByUser(User user);

    BallotResult getBallotAnswerById(Long id);

    BallotResult saveBallotAnswer(BallotResult ballot);

    void deleteBallotAnswer(Long id);
}
