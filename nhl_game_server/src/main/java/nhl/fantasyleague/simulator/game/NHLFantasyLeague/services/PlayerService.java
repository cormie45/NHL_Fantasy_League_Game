package nhl.fantasyleague.simulator.game.NHLFantasyLeague.services;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Player;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    public ResponseEntity<List<Player>> getAllPlayers(){
        return new ResponseEntity<>(playerRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity getPlayer(Long id){
        return new ResponseEntity<>(playerRepository.findById(id), HttpStatus.OK);
    }

    public ResponseEntity<List<Player>> findByTeamId(Long team) {
        return new ResponseEntity<>(playerRepository.findByTeamId(team), HttpStatus.OK);
    }
}
