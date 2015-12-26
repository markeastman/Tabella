package uk.me.eastmans.tabella.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.me.eastmans.tabella.domain.BallotResult;
import uk.me.eastmans.tabella.repositories.BallotResultRepository;

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
    public Iterable<BallotResult> listAllBallotAnswers() {
        return ballotAnswerRepository.findAll();
    }

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
