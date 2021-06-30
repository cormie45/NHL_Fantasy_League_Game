package nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
