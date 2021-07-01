package nhl.fantasyleague.simulator.game.NHLFantasyLeague.controllers;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Coach;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.services.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CoachController {

    @Autowired
    CoachService coachService;

    @GetMapping(value = "/coaches")
    public ResponseEntity<List<Coach>> getAllCoaches(){
        return coachService.getAllCoaches();
    }

    @GetMapping(value = "/coaches/{id}")
    public ResponseEntity getCoach(@PathVariable Long id){
        return coachService.getCoach(id);
    }
}
