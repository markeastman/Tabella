package uk.me.eastmans.tabella.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import uk.me.eastmans.tabella.domain.Ballot;
import uk.me.eastmans.tabella.repositories.BallotRepository;
import org.springframework.stereotype.Service;

/**
 * Created by meastman on 22/12/15.
 */
@Service
public class BallotServiceImpl implements BallotService {
    private BallotRepository ballotRepository;

    @Autowired
    public void setBallotRepository(BallotRepository ballotRepository) {
        this.ballotRepository = ballotRepository;
    }

    @Override
    public Iterable<Ballot> listAllBallots() {
        return ballotRepository.findAll();
    }

    @Override
    public Iterable<Ballot> getAllOpenBallots() {
        return ballotRepository.findByAnswerIndexOrderByIdDesc(-1);
    }

    @Override
    public Ballot getBallotById(Long id) {
        return ballotRepository.findOne(id);
    }

    @Override
    public Ballot saveBallot(Ballot ballot) {
        return ballotRepository.save(ballot);
    }

    @Override
    public void deleteBallot(Long id) {
        ballotRepository.delete(id);
    }
}
