package nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findByTeamId(Long id);
}
