package uk.me.eastmans.tabella.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uk.me.eastmans.tabella.domain.Ballot;
import uk.me.eastmans.tabella.domain.User;

import java.util.List;

/**
 * Created by meastman on 22/12/15.
 */
public interface BallotRepository extends CrudRepository<Ballot, Long> {

    @Query("select b from Ballot b where b.user <> ?1 and b not in (select ba.ballot from BallotResult ba where user = ?1 )")
    public List<Ballot> getUnasweredBallotsForUser(User user);

    @Query("select distinct br.ballot, br.answerIndex, count(*) from BallotResult br where br.ballot.user = ?1 group by br.ballot, br.answerIndex")
    public List<Object[]> getAnsweredBallots(User user);

    public Long countByUser(User user );

    @Query("select count(distinct b) from Ballot b, BallotResult ba where ba.ballot = b and b.user = ?1 )")
    public Long getBallotCountAnsweredForUser(User user);
}
