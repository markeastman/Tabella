package uk.me.eastmans.tabella.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import uk.me.eastmans.tabella.domain.Ballot;
import uk.me.eastmans.tabella.domain.BallotResult;
import uk.me.eastmans.tabella.domain.User;
import uk.me.eastmans.tabella.repositories.BallotRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Ballot> getUnasweredBallotsForUser(User user ) { return ballotRepository.getUnasweredBallotsForUser(user); }

    @Override
    public Map<Ballot,Map<Integer,Long>> getAnsweredBallots( User user ) {
        Map<Ballot,Map<Integer,Long>> ballotResults = new HashMap<>();
        List<Object[]> results = ballotRepository.getAnsweredBallots(user);
        // We need to change to a ballot and then map of index to answers
        for (Object[] result : results)
        {
            Ballot b = (Ballot)result[0];
            Integer index = (Integer)result[1];
            Long count = (Long)result[2];
            if (!ballotResults.containsKey(b))
                ballotResults.put(b,new HashMap<Integer,Long>());
            Map<Integer,Long> answerCount = ballotResults.get(b);
            answerCount.put(index,count);
        }
        return ballotResults;
    }

    public long getBallotCountForUser(User user) { return ballotRepository.countByUser(user); }
    public long getBallotCountAnsweredForUser(User user) { return ballotRepository.getBallotCountAnsweredForUser(user); }

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
