package nhl.fantasyleague.simulator.game.NHLFantasyLeague.controllers;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Team;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @GetMapping(value = "/teams")
    public ResponseEntity<List<Team>> getAllTeams(){
        return new ResponseEntity<>(teamRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/teams/{id}")
    public ResponseEntity getTeam(@PathVariable Long id){
        return new ResponseEntity<>(teamRepository.findById(id), HttpStatus.OK);
    }
}
