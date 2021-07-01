package nhl.fantasyleague.simulator.game.NHLFantasyLeague.controllers;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Game;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {

    @Autowired
    GameService gameService;

    @GetMapping(value = "/games")
    public ResponseEntity<List<Game>> getAllGames(){
        return gameService.getAllGames();
    }

    @GetMapping(value = "/games/{id}")
    public ResponseEntity getGame(@PathVariable Long id){
        return gameService.getGame(id);
    }
}
