package uk.me.eastmans.tabella.services;

import uk.me.eastmans.tabella.domain.Ballot;
import uk.me.eastmans.tabella.domain.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by meastman on 22/12/15.
 */
@ApplicationScoped
public class BallotServiceImpl implements BallotService {

    @Inject
    private EntityManager em;

    @Override
    public Iterable<Ballot> listAllBallots() {
        TypedQuery<Ballot> query = em.createQuery(
                "SELECT b FROM Ballot b", Ballot.class);
        return query.getResultList();
    }

    @Override
    public List<Ballot> getUnasweredBallotsForUser(User user ) {
        TypedQuery<Ballot> query = em.createQuery(
                "select b from Ballot b where b.user.id <> :userId and b not in (select ba.ballot from BallotResult ba where user.id = :userId )", Ballot.class);
        query.setParameter("userId", user.getId());
        return query.getResultList();
    }

    @Override
    public Map<Ballot,Map<Integer,Long>> getAnsweredBallots(User user ) {
        Map<Ballot,Map<Integer,Long>> ballotResults = new HashMap<>();
        Query query = em.createQuery(
                "select distinct br.ballot, br.answerIndex, count(*) from BallotResult br where br.ballot.user.id = :userId group by br.ballot, br.answerIndex", Ballot.class);
        query.setParameter("userId", user.getId());

        List<Object[]> results = query.getResultList();
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

    public long getBallotCountForUser(User user) {
        TypedQuery<Long> query = em.createQuery(
                "select count(*) from Ballot b where b.user.id = :userId ", Long.class);
        query.setParameter("userId", user.getId());
        return query.getSingleResult().longValue();
    }

    public long getBallotCountAnsweredForUser(User user) {
        TypedQuery<Long> query = em.createQuery(
                "select count(distinct b) from Ballot b, BallotResult ba where ba.ballot = b and b.user.id = :userId", Long.class);
        query.setParameter("userId", user.getId());
        return query.getSingleResult().longValue();
    }

    @Override
    public Ballot getBallotById(Long id) {
        return em.find(Ballot.class, id);
    }

    @Override
    public Ballot saveBallot(Ballot ballot) {
        em.persist(ballot);
        return ballot;
    }

    @Transactional
    public void deleteBallot(Long id) {
        Ballot ballot = em.find(Ballot.class, id);
        em.remove(ballot);
    }
}
