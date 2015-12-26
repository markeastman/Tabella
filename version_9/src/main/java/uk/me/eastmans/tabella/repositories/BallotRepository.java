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

    @Query("select b from Ballot b where b not in (select ba.ballot from BallotResult ba where user = ?1 )")
    public List<Ballot> getUnasweredBallotsForUser(User user);
}
