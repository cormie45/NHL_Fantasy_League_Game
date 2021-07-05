package nhl.fantasyleague.simulator.game.NHLFantasyLeague.services;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Goal;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    @Autowired
    GoalRepository goalRepository;

    public ResponseEntity<List<Goal>> getAllGoals() {
        return new ResponseEntity<>(goalRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity getGoal(Long id) {
        return new ResponseEntity<>(goalRepository.findById(id), HttpStatus.OK);
    }
}
