package nhl.fantasyleague.simulator.game.NHLFantasyLeague.services;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Coach;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachService {

    @Autowired
    CoachRepository coachRepository;

    public ResponseEntity<List<Coach>> getAllCoaches() {
        return new ResponseEntity<>(coachRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity getCoach(Long id) {
        return new ResponseEntity<>(coachRepository.findById(id), HttpStatus.OK);
    }
}
