package nhl.fantasyleague.simulator.game.NHLFantasyLeague.services;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Game;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Team;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories.GameRepository;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    TeamRepository teamRepository;

    public ResponseEntity<List<Game>> getAllGames(){
        return new ResponseEntity<>(gameRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity getGame(Long id){
        return new ResponseEntity<>(gameRepository.findById(id), HttpStatus.OK);
    }

    public ResponseEntity createGames(){
        ArrayList<Team> teams = (ArrayList<Team>) teamRepository.findAll();
        Collections.shuffle(teams, new Random());
        int size = teams.size();
        int sizeHalf = Math.floorDiv(size, 2);
        ArrayList<Game> homeGames = new ArrayList<>();
        ArrayList<Game> awayGames = new ArrayList<>();
        ArrayList<Game> allGames = new ArrayList<>();
        int gameId = 1;
        for (int i = 1; i < size - 1; i++){
            for(int n = 0; n < sizeHalf -1; n++){
                Game hGame = new Game(teams.get(n), teams.get(size - 1 - i));
                hGame
                homeGames.add(hGame);
//                System.out.println(homeGames);
                Game aGame = new Game(teams.get(size - 1 - i), teams.get(n));
                awayGames.add(aGame);
//                System.out.println(awayGames);
            }
            teams.add(1, teams.remove(teams.size() - 1));
            allGames.addAll(Math.floorDiv(allGames.size(), 2), homeGames);
            allGames.addAll(awayGames);
            homeGames.clear();
            awayGames.clear();
        }
        return new ResponseEntity(allGames, HttpStatus.OK);
    }

}
