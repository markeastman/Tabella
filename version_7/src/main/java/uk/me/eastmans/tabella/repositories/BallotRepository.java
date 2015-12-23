package uk.me.eastmans.tabella.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.me.eastmans.tabella.domain.Ballot;

import java.util.List;

/**
 * Created by meastman on 22/12/15.
 */
public interface BallotRepository extends CrudRepository<Ballot, Long> {
    List<Ballot> findByAnswerIndexOrderByIdDesc(int answerIndex);
}
