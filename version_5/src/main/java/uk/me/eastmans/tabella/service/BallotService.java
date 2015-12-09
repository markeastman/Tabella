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
        Query query = em.createQuery("SELECT b FROM Ballot b");
        List<Ballot> ballots = query.getResultList();
        return ballots;
    }

    public void addBallot( String question, List<String> answers )
    {
        Ballot ballot = new Ballot( question, answers );
        em.persist( ballot );
    }
}
