package nhl.fantasyleague.simulator.game.NHLFantasyLeague.services;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Team;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

    public ResponseEntity<List<Team>> getAllTeams(){
        return new ResponseEntity<>(teamRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity getTeam(Long id){
        return new ResponseEntity<>(teamRepository.findById(id), HttpStatus.OK);
    }
}
