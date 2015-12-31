package uk.me.eastmans.tabella.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import uk.me.eastmans.tabella.domain.Ballot;
import uk.me.eastmans.tabella.domain.BallotResult;
import uk.me.eastmans.tabella.domain.User;
import uk.me.eastmans.tabella.repositories.BallotResultRepository;

import java.util.List;

/**
 * Created by meastman on 22/12/15.
 */
@Service
public class BallotResultServiceImpl implements BallotResultService {
    private BallotResultRepository ballotAnswerRepository;

    @Autowired
    public void setBallotAnswerRepository(BallotResultRepository ballotAnswerRepository) {
        this.ballotAnswerRepository = ballotAnswerRepository;
    }

    @Override
    public long getBallotCountUnansweredByUser(User user) { return ballotAnswerRepository.getBallotCountUnansweredByUser(user); }

    @Override
    public long getBallotCountAnsweredByUser(User user) { return ballotAnswerRepository.getBallotCountAnsweredByUser(user); }

    @Override
    public Iterable<BallotResult> listAllBallotAnswers() {
        return ballotAnswerRepository.findAll();
    }

    @Override
    public List<BallotResult> getBallotAnswers(Ballot b) { return ballotAnswerRepository.getByBallot(b); }

    @Override
    public BallotResult getBallotAnswerById(Long id) {
        return ballotAnswerRepository.findOne(id);
    }

    @Override
    public BallotResult saveBallotAnswer(BallotResult ballot) {
        return ballotAnswerRepository.save(ballot);
    }

    @Override
    public void deleteBallotAnswer(Long id) {
        ballotAnswerRepository.delete(id);
    }
}
