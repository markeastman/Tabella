package uk.me.eastmans.tabella.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uk.me.eastmans.tabella.domain.Ballot;
import uk.me.eastmans.tabella.domain.BallotResult;
import uk.me.eastmans.tabella.domain.User;

import java.util.List;

/**
 * Created by meastman on 22/12/15.
 */
public interface BallotResultRepository extends CrudRepository<BallotResult, Long> {

    @Query("select count(b) from Ballot b where b not in (select ba.ballot from BallotResult ba where ba.user = ?1 ) and b.user <> ?1")
    public long getBallotCountUnansweredByUser(User user);

    @Query("select count(ba) from BallotResult ba where ba.user = ?1 )")
    public long getBallotCountAnsweredByUser(User user);

    public List<BallotResult> getByBallot(Ballot b);

}
