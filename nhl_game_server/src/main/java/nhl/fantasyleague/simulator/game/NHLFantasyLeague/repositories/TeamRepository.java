package nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
