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
import java.util.stream.IntStream;

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
        ArrayList<Game> homeGames = new ArrayList<>();
        ArrayList<Game> awayGames = new ArrayList<>();
        ArrayList<Game> allGames = new ArrayList<>();
        IntStream.range(1, size).forEach(n -> {
            IntStream.range(0, size/2).forEach(i ->{
                Game hGame = new Game(teams.get(i), teams.get(size - 1 - i));
                homeGames.add(hGame);
//                System.out.println(homeGames);
                Game aGame = new Game(teams.get(size - 1 - i), teams.get(i));
                awayGames.add(aGame);
//                System.out.println(awayGames);
            });
            teams.add(1, teams.remove(teams.size() - 1));
            allGames.addAll(allGames.size()/2, homeGames);
            allGames.addAll(awayGames);
            allGames.forEach(g -> gameRepository.save(g));
            homeGames.clear();
            awayGames.clear();
        });

        return new ResponseEntity(allGames, HttpStatus.OK);
    }

    public ResponseEntity<List<Game>> playWeek1() {
        ArrayList<Game> allGames = (ArrayList<Game>) gameRepository.findAll();
        ArrayList<Game> week1Games = new ArrayList<>();
        IntStream.range(0, 12).forEach(i ->{
            Game currentGame = allGames.get(i);
            week1Games.add(currentGame);
        });
        return new ResponseEntity(week1Games, HttpStatus.OK);
    }

    public ResponseEntity<List<Game>> playWeek2() {
        ArrayList<Game> allGames = (ArrayList<Game>) gameRepository.findAll();
        ArrayList<Game> week2Games = new ArrayList<>();
        IntStream.range(12, 24).forEach(i ->{
            Game currentGame = allGames.get(i);
            week2Games.add(currentGame);
        });
        return new ResponseEntity(week2Games, HttpStatus.OK);
    }
}