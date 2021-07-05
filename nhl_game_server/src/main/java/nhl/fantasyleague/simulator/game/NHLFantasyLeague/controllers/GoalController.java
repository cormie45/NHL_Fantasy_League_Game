package nhl.fantasyleague.simulator.game.NHLFantasyLeague.controllers;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Goal;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.services.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GoalController {

    @Autowired
    GoalService goalService;

    @GetMapping(value = "/goals")
    public ResponseEntity<List<Goal>> getAllGoals(){
        return goalService.getAllGoals();
    }

    @GetMapping(value = "/goals/{id}")
    public ResponseEntity getGoal(@PathVariable Long id){
        return goalService.getGoal(id);
    }
}
