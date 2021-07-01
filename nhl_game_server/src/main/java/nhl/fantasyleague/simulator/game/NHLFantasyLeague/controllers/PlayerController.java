package nhl.fantasyleague.simulator.game.NHLFantasyLeague.controllers;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Player;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping(value = "/players")
    public ResponseEntity<List<Player>> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    @GetMapping(value = "/players/{id}")
    public ResponseEntity getPlayer(@PathVariable Long id){
        return playerService.getPlayer(id);
    }
}
