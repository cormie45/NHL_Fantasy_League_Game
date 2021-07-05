package nhl.fantasyleague.simulator.game.NHLFantasyLeague.services;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Game;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Player;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Team;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories.GameRepository;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories.PlayerRepository;
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

    @Autowired
    PlayerRepository playerRepository;

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

    public ResponseEntity<Game> simulateGames(Long id) {

        Game game = gameRepository.findById(id).get();
        Team homeTeam = game.getHomeTeam();
        Team awayTeam = game.getAwayTeam();

        ArrayList<Player> homeTeamPlayers = (ArrayList<Player>) playerRepository.findByTeamId(homeTeam.getId());
        ArrayList<Player> awayTeamPlayers = (ArrayList<Player>) playerRepository.findByTeamId(awayTeam.getId());

        int homePlayerAtt = homeTeamPlayers.stream().filter(
                player -> player.getPosition().equals("Center") || player.getPosition().equals("Left Wing") || player.getPosition().equals("Right Wing")).mapToInt(
                        player -> (player.getRating() * 2) * player.getPlayerForm() * getTimeOnIce(player)).sum();
        int homeTeamAtt = Math.floorDiv(homePlayerAtt * homeTeam.getTeamForm(), 10);

        int homePlayerDef = homeTeamPlayers.stream().filter(
                player -> player.getPosition().equals("Defence") || player.getPosition().equals("Goaltender")).mapToInt(
                        player -> (player.getRating() * 2) * player.getPlayerForm() * getTimeOnIce(player)).sum();
        int homeTeamDef = Math.floorDiv(homePlayerDef * homeTeam.getTeamForm(), 10);

        int awayPlayerAtt = awayTeamPlayers.stream().filter(
                player -> player.getPosition().equals("Center") || player.getPosition().equals("Left Wing") || player.getPosition().equals("Right Wing")).mapToInt(
                        player -> (player.getRating() * 2) * player.getPlayerForm() * getTimeOnIce(player)).sum();
        int awayTeamAtt = Math.floorDiv(awayPlayerAtt * awayTeam.getTeamForm(), 10);

        int awayPlayerDef = awayTeamPlayers.stream().filter(
                player -> player.getPosition().equals("Defence") || player.getPosition().equals("Goaltender")).mapToInt(
                        player -> (player.getRating() * 2) * player.getPlayerForm() * getTimeOnIce(player)).sum();
        int awayTeamDef = Math.floorDiv(awayPlayerDef * awayTeam.getTeamForm(), 10);

        if (!game.hasPlayed1st()){
            int homeGoals = generateScore(homeTeamAtt, awayTeamDef);
            int awayGoals = generateScore(awayTeamAtt, homeTeamDef);
            game.setHomeGoals1st(homeGoals);
            game.setTotalHome(game.getTotalHome() + homeGoals);
            homeTeam.setGoalsFor(homeTeam.getGoalsFor() + homeGoals);
            awayTeam.setGoalsAgainst(awayTeam.getGoalsAgainst() + homeGoals);
            game.setAwayGoals1st(awayGoals);
            game.setTotalAway(game.getTotalAway() + awayGoals);
            awayTeam.setGoalsFor(awayTeam.getGoalsFor() + awayGoals);
            homeTeam.setGoalsAgainst(homeTeam.getGoalsAgainst() + awayGoals);
            if (game.getTotalHome() > game.getTotalAway()){
                homeTeam.setPotentialPoints(homeTeam.getPoints() + 2);
            }
            if (game.getTotalHome() < game.getTotalAway()){
                awayTeam.setPotentialPoints(awayTeam.getPoints() + 2);
            }

            if (game.getTotalHome() == game.getTotalAway()){
                homeTeam.setPotentialPoints(homeTeam.getPoints() + 1);
                awayTeam.setPotentialPoints(awayTeam.getPoints() + 1);
            }
            game.setPlayed1st(true);
            gameRepository.save(game);
            return new ResponseEntity(game, HttpStatus.OK);
        }

        if (game.hasPlayed1st() && !game.hasPlayed2nd()){
            int homeGoals = generateScore(homeTeamAtt, awayTeamDef);
            int awayGoals = generateScore(awayTeamAtt, homeTeamDef);
            game.setHomeGoals2nd(homeGoals);
            game.setTotalHome(game.getTotalHome() + homeGoals);
            homeTeam.setGoalsFor(homeTeam.getGoalsFor() + homeGoals);
            awayTeam.setGoalsAgainst(awayTeam.getGoalsAgainst() + homeGoals);
            game.setAwayGoals2nd(awayGoals);
            game.setTotalAway(game.getTotalAway() + awayGoals);
            awayTeam.setGoalsFor(awayTeam.getGoalsFor() + awayGoals);
            homeTeam.setGoalsAgainst(homeTeam.getGoalsAgainst() + awayGoals);
            if (game.getTotalHome() > game.getTotalAway()){
                homeTeam.setPotentialPoints(homeTeam.getPoints() + 2);
            }
            if (game.getTotalHome() < game.getTotalAway()){
                awayTeam.setPotentialPoints(awayTeam.getPoints() + 2);
            }

            if (game.getTotalHome() == game.getTotalAway()){
                homeTeam.setPotentialPoints(homeTeam.getPoints() + 1);
                awayTeam.setPotentialPoints(awayTeam.getPoints() + 1);
            }
            game.setPlayed2nd(true);
            gameRepository.save(game);
            return new ResponseEntity(game, HttpStatus.OK);
        }

        if (game.hasPlayed1st() && game.hasPlayed2nd() && !game.hasPlayed3rd()){
            int homeGoals = generateScore(homeTeamAtt, awayTeamDef);
            int awayGoals = generateScore(awayTeamAtt, homeTeamDef);
            game.setHomeGoals3rd(homeGoals);
            game.setTotalHome(game.getTotalHome() + homeGoals);
            homeTeam.setGoalsFor(homeTeam.getGoalsFor() + homeGoals);
            awayTeam.setGoalsAgainst(awayTeam.getGoalsAgainst() + homeGoals);
            game.setAwayGoals3rd(awayGoals);
            game.setTotalAway(game.getTotalAway() + awayGoals);
            awayTeam.setGoalsFor(awayTeam.getGoalsFor() + awayGoals);
            homeTeam.setGoalsAgainst(homeTeam.getGoalsAgainst() + awayGoals);
            if (game.getTotalHome() > game.getTotalAway()){
                homeTeam.setPotentialPoints(homeTeam.getPoints() + 2);
            }
            if (game.getTotalHome() < game.getTotalAway()){
                awayTeam.setPotentialPoints(awayTeam.getPoints() + 2);
            }

            if (game.getTotalHome() == game.getTotalAway()){
                homeTeam.setPotentialPoints(homeTeam.getPoints() + 1);
                awayTeam.setPotentialPoints(awayTeam.getPoints() + 1);
            }
            game.setPlayed3rd(true);
            gameRepository.save(game);
            return new ResponseEntity(game, HttpStatus.OK);
        }
        return new ResponseEntity(game, HttpStatus.OK);
    }

    private int getTimeOnIce(Player player){
        int timeOnIce = 0;
        if (player.getLine() == 1){
            timeOnIce = 3;
        }
        if (player.getLine() == 2){
            timeOnIce = 2;
        }
        if (player.getLine() == 3){
            timeOnIce = 1;
        }
        return timeOnIce;
    }

    private int generateScore(int Att, int Def) {

        ArrayList<Integer> potentialScore = new ArrayList<>();
        Random randGoal = new Random();
        int max = 4;
        int min = 0;

        if (Att > Def){

            int variance = Att - Def;
            for (int n=1; n<variance; n++){

                if (n > 14){
                    min = 1;
                    max = 5;
                    int goal = randGoal.nextInt((max - min) +1) + min;
                    potentialScore.add(goal);
                }

                else if (n > 9){
                    max = 3;
                    int goal = randGoal.nextInt((max - min) +1) + min;
                    potentialScore.add(goal);
                }

                else if (n > 4){
                    max = 2;
                    int goal = randGoal.nextInt((max - min) +1) + min;
                    potentialScore.add(goal);
                }

                else{
                    max = 1;
                    int goal = randGoal.nextInt((max - min) +1) + min;
                    potentialScore.add(goal);
                }

            }
        }
        if(Def > Att){

            int variance = Def - Att;
            for (int n=1; n<variance; n++){
                if (n > 14){
                    max = 1;
                    int goal = randGoal.nextInt(1) + min;
                    potentialScore.add(goal);
                }

                else if (n > 9){
                    max = 2;
                    int goal = randGoal.nextInt((max - min) +1) + min;
                    potentialScore.add(goal);
                }

                else if (n > 4){
                    max = 3;
                    int goal = randGoal.nextInt((max - min) +1) + min;
                    potentialScore.add(goal);
                }
                else {
                    int goal = randGoal.nextInt((max - min) +1) + min;
                    potentialScore.add(goal);
                }
            }
        }

        if (Att == Def){
            potentialScore.add(0);
            potentialScore.add(0);
            potentialScore.add(1);
            potentialScore.add(2);
        }

        Random randScore = new Random();
        return potentialScore.get(randScore.nextInt(potentialScore.size()));
    }



//    public ResponseEntity<List<Game>> playWeek1() {
//        ArrayList<Game> allGames = (ArrayList<Game>) gameRepository.findAll();
//        ArrayList<Game> week1Games = new ArrayList<>();
//        IntStream.range(0, 12).forEach(i ->{
//            Game currentGame = allGames.get(i);
//            week1Games.add(currentGame);
//        });
//        return new ResponseEntity(week1Games, HttpStatus.OK);
//    }
//
//    public ResponseEntity<List<Game>> playWeek2() {
//        ArrayList<Game> allGames = (ArrayList<Game>) gameRepository.findAll();
//        ArrayList<Game> week2Games = new ArrayList<>();
//        IntStream.range(12, 24).forEach(i ->{
//            Game currentGame = allGames.get(i);
//            week2Games.add(currentGame);
//        });
//        return new ResponseEntity(week2Games, HttpStatus.OK);
//    }
}
