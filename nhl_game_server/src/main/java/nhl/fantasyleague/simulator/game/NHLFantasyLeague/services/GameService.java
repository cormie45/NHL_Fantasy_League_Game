package nhl.fantasyleague.simulator.game.NHLFantasyLeague.services;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Game;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    public ResponseEntity<List<Game>> getAllGames(){
        return new ResponseEntity<>(gameRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity getGame(Long id){
        return new ResponseEntity<>(gameRepository.findById(id), HttpStatus.OK);
    }

}
