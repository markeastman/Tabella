package uk.me.eastmans.tabella.service;

import uk.me.eastmans.tabella.data.Ballot;

import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by meastman on 03/12/15.
 */
@Stateful
@ApplicationScoped
public class BallotService
{
    @Inject
    private EntityManager em;

    public List<Ballot> getAllBallots()
    {
        Query query = em.createQuery("SELECT b FROM Ballot b where answerIndex = -1 order by id desc");
        query.setMaxResults(50);
        List<Ballot> ballots = query.getResultList();
        return ballots;
    }

    public void addBallot( String question, List<String> answers )
    {
        Ballot ballot = new Ballot( question, answers );
        em.persist( ballot );
    }

    public void answerBallot( String ballotId, String answerIndex )
    {
        // Get the ballot
        Ballot b = getBallot( ballotId );
        if (b != null)
        {
            int index = Integer.parseInt(answerIndex);
            // get the answers and check the index
            if (index >= 0 && index < b.getAnswers().size())
            {
                // Add the answer for this person
                b.setAnswerIndex( index );
                em.persist(b);
            }
        }
    }

    public Ballot getBallot(String stringId)
    {
        Long id = Long.parseLong(stringId);
        Query query = em.createQuery("select b from Ballot b where id = :id");
        query.setParameter("id",id);
        return (Ballot)query.getSingleResult();
    }
}
