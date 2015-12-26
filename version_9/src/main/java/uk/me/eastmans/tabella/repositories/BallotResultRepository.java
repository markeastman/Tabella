package uk.me.eastmans.tabella.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.me.eastmans.tabella.domain.BallotResult;

/**
 * Created by meastman on 22/12/15.
 */
public interface BallotResultRepository extends CrudRepository<BallotResult, Long> {
}
