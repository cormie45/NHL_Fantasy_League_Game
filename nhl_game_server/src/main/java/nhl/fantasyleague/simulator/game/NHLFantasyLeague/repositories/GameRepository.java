package nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
