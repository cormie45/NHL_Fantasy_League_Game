package nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}
