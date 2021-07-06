package nhl.fantasyleague.simulator.game.NHLFantasyLeague.services;

import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Game;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Goal;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Player;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.models.Team;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories.GameRepository;
import nhl.fantasyleague.simulator.game.NHLFantasyLeague.repositories.GoalRepository;
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

    @Autowired
    GoalRepository goalRepository;

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
        ArrayList<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(homeTeamPlayers);
        allPlayers.addAll(awayTeamPlayers);

        int homeTeamAtt = Math.floorDiv(getPlayersAttackRating(homeTeamPlayers) * homeTeam.getTeamForm(), 10);
        int homeTeamDef = Math.floorDiv(getPlayerDefenceRating(homeTeamPlayers) * homeTeam.getTeamForm(), 10);
        int awayTeamAtt = Math.floorDiv(getPlayersAttackRating(awayTeamPlayers) * awayTeam.getTeamForm(), 10);
        int awayTeamDef = Math.floorDiv(getPlayerDefenceRating(awayTeamPlayers) * awayTeam.getTeamForm(), 10);

        if (!game.hasPlayed1st()){
            int homeGoals = generateScore(homeTeamAtt, awayTeamDef);
            int awayGoals = generateScore(awayTeamAtt, homeTeamDef);
            game.setPlayed1st(true);
            game.setHomeGoals1st(homeGoals);
            if (homeGoals > 0){
                generateGoals(game, homeTeamPlayers, homeGoals);
            }
            game.setTotalHome(game.getTotalHome() + homeGoals);
            homeTeam.setGoalsFor(homeTeam.getGoalsFor() + homeGoals);
            awayTeam.setGoalsAgainst(awayTeam.getGoalsAgainst() + homeGoals);
            game.setAwayGoals1st(awayGoals);
            if(awayGoals > 0){
                generateGoals(game, awayTeamPlayers, awayGoals);
            }
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
            gameRepository.save(game);
            return new ResponseEntity(game, HttpStatus.OK);
        }

        if (game.hasPlayed1st() && !game.hasPlayed2nd()){
            int homeGoals = generateScore(homeTeamAtt, awayTeamDef);
            int awayGoals = generateScore(awayTeamAtt, homeTeamDef);
            game.setPlayed2nd(true);
            game.setHomeGoals2nd(homeGoals);
            if (homeGoals > 0){
                generateGoals(game, homeTeamPlayers, homeGoals);
            }
            game.setTotalHome(game.getTotalHome() + homeGoals);
            homeTeam.setGoalsFor(homeTeam.getGoalsFor() + homeGoals);
            awayTeam.setGoalsAgainst(awayTeam.getGoalsAgainst() + homeGoals);
            game.setAwayGoals2nd(awayGoals);
            if (awayGoals > 0){
                generateGoals(game, awayTeamPlayers, awayGoals);
            }
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
            gameRepository.save(game);
            return new ResponseEntity(game, HttpStatus.OK);
        }

        if (game.hasPlayed1st() && game.hasPlayed2nd() && !game.hasPlayed3rd()){
            int homeGoals = generateScore(homeTeamAtt, awayTeamDef);
            int awayGoals = generateScore(awayTeamAtt, homeTeamDef);
            game.setPlayed3rd(true);
            game.setHomeGoals3rd(homeGoals);
            if (homeGoals > 0){
                generateGoals(game, homeTeamPlayers, homeGoals);
            }
            game.setTotalHome(game.getTotalHome() + homeGoals);
            homeTeam.setGoalsFor(homeTeam.getGoalsFor() + homeGoals);
            awayTeam.setGoalsAgainst(awayTeam.getGoalsAgainst() + homeGoals);
            game.setAwayGoals3rd(awayGoals);
            if (awayGoals > 0){
                generateGoals(game, awayTeamPlayers, awayGoals);
            }
            game.setTotalAway(game.getTotalAway() + awayGoals);
            awayTeam.setGoalsFor(awayTeam.getGoalsFor() + awayGoals);
            homeTeam.setGoalsAgainst(homeTeam.getGoalsAgainst() + awayGoals);
            homeTeam.setGamesPlayed(homeTeam.getGamesPlayed() + 1);
            awayTeam.setGamesPlayed(awayTeam.getGamesPlayed() + 1);

            if (game.getTotalHome() > game.getTotalAway()){
                homeTeam.setPoints(homeTeam.getPoints() + 2);
                homeTeam.setPotentialPoints(0);
                if (homeTeam.getTeamForm()< 5){
                    homeTeam.setTeamForm(homeTeam.getTeamForm() + 1);
                }
                if(game.getTotalAway() < 2){
                    homeTeamPlayers.forEach(this::raiseDefensivePlayerForm);
                    raiseDefensiveLineForm(homeTeamPlayers);
                }

                if (game.getTotalAway() > 3){
                    homeTeamPlayers.forEach(this::lowerDefensivePlayerForm);
                }

                if (game.getTotalHome() > 3){
                    homeTeamPlayers.forEach(this::raiseAttackingPlayerForm);
                }

                awayTeam.setPotentialPoints(0);
                if (awayTeam.getTeamForm() > 1){
                    awayTeam.setTeamForm(awayTeam.getTeamForm() - 1);
                }
                if (game.getTotalHome() <= 3){
                    awayTeamPlayers.forEach(this::lowerDefensivePlayerForm);
                }
                if (game.getTotalHome() > 3){
                    awayTeamPlayers.forEach(this::lowerDefensivePlayerForm);
                    awayTeamPlayers.forEach(this::lowerDefensivePlayerForm);
                }
                if (game.getTotalAway() < 2){
                    awayTeamPlayers.forEach(this::lowerAttackingPlayerForm);
                }
            }

            if (game.getTotalHome() < game.getTotalAway()){
                homeTeam.setPotentialPoints(0);
                if (homeTeam.getTeamForm() > 1){
                    homeTeam.setTeamForm(homeTeam.getTeamForm() - 1);
                }
                if (game.getTotalAway() <= 3){
                    homeTeamPlayers.forEach(this::lowerDefensivePlayerForm);
                }
                if (game.getTotalAway() > 3){
                    homeTeamPlayers.forEach(this::lowerDefensivePlayerForm);
                    homeTeamPlayers.forEach(this::lowerDefensivePlayerForm);
                }

                if(game.getTotalHome() < 2){
                    homeTeamPlayers.forEach(this::lowerAttackingPlayerForm);
                }

                awayTeam.setPoints(awayTeam.getPoints() + 2);
                awayTeam.setPotentialPoints(0);
                if (awayTeam.getTeamForm()< 5){
                    awayTeam.setTeamForm(awayTeam.getTeamForm() + 1);
                }
                if(game.getTotalHome() < 2){
                    awayTeamPlayers.forEach(this::raiseDefensivePlayerForm);
                    raiseDefensiveLineForm(awayTeamPlayers);
                }

                if (game.getTotalHome() > 3){
                    awayTeamPlayers.forEach(this::lowerDefensivePlayerForm);
                }

                if (game.getTotalAway() > 3){
                    awayTeamPlayers.forEach(this::raiseAttackingPlayerForm);
                }
            }

            if (game.getTotalHome() == game.getTotalAway()){
                homeTeam.setPoints(homeTeam.getPoints() + 1);
                homeTeam.setPotentialPoints(0);
                awayTeam.setPoints(awayTeam.getPoints() + 1);
                awayTeam.setPotentialPoints(0);

                if (game.getTotalHome() < 2) {
                    allPlayers.forEach(this::raiseDefensivePlayerForm);
                    raiseDefensiveLineForm(homeTeamPlayers);
                    raiseDefensiveLineForm(awayTeamPlayers);
                }

                if (game.getTotalHome() > 2){
                    allPlayers.forEach(this::lowerDefensivePlayerForm);
                }
            }
            gameRepository.save(game);
            return new ResponseEntity(game, HttpStatus.OK);
        }
        return new ResponseEntity(game, HttpStatus.OK);
    }

    private void generateGoals(Game game, ArrayList<Player> players, int goalsScored) {
        int period = 0;
        if (game.hasPlayed1st() && !game.hasPlayed2nd() && !game.hasPlayed3rd()){
            period = 1;
        }
        if (game.hasPlayed1st() && game.hasPlayed2nd() && !game.hasPlayed3rd()){
            period = 2;
        }
        if (game.hasPlayed1st() && game.hasPlayed2nd() && game.hasPlayed3rd()){
            period = 3;
        }

        ArrayList<Player> possibleGoalscorer = new ArrayList<>();

        for (int g=0; g<goalsScored; g++) {
            players.forEach(player -> {
                if (player.getPosition().equals("Center")){
                    int goalAttempts = 3 + getTimeOnIce(player) + player.getPlayerForm();
                    for (int i = 1; i<goalAttempts;i++){
                        possibleGoalscorer.add(player);
                    }
                }

                if (player.getPosition().equals("Left Wing") || player.getPosition().equals("Right Wing")){
                    int goalAttempts = 2 + getTimeOnIce(player) + player.getPlayerForm();
                    for (int i = 1; i<goalAttempts; i++){
                        possibleGoalscorer.add(player);
                    }
                }

                if(player.getPosition().equals("Defence")){
                    int goalAttempts = Math.floorDiv(1 + getTimeOnIce(player) + player.getPlayerForm(), 2);
                    for (int i = 1; i<goalAttempts; i++){
                        possibleGoalscorer.add(player);
                    }
                }
            });
            System.out.println(possibleGoalscorer.size());
            Random randScorer = new Random();
            Player goalScorer = possibleGoalscorer.get(randScorer.nextInt(possibleGoalscorer.size()));
            Goal goal = new Goal(goalScorer, game, period);
            goalRepository.save(goal);
            goalScorer.setPoints(goalScorer.getPoints() + 1);
            if (goalScorer.getPosition().equals("Defence")){
                raiseDefensivePlayerForm(goalScorer);
            }else{
                raiseAttackingPlayerForm(goalScorer);
            }
        }
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
            for (int n=0; n<variance; n++){

                if (n > 19){
                    min = 1;
                    max = 5;
                    int goal = randGoal.nextInt((max - min) +1) + min;
                    potentialScore.add(goal);
                }

                else if (n > 14){
                    max = 4;
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
            for (int n=0; n<variance; n++){
                if (n > 14){
                    max = 1;
                    potentialScore.add(0);
                    int goal = randGoal.nextInt(1) + min;
                    potentialScore.add(goal);
                }

                else if (n > 9){
                    max = 2;
                    potentialScore.add(0);
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
            potentialScore.add(1);
            potentialScore.add(2);
        }

        Random randScore = new Random();
        potentialScore.add(0);
        return potentialScore.get(randScore.nextInt(potentialScore.size()));
    }

    private int getPlayersAttackRating(ArrayList<Player> players){
        return players.stream().filter(
                player -> player.getPosition().equals("Center") || player.getPosition().equals("Left Wing") || player.getPosition().equals("Right Wing")).mapToInt(
                player -> (player.getRating() * 2) * player.getPlayerForm() * getTimeOnIce(player)).sum();
    }

    private int getPlayerDefenceRating(ArrayList<Player> players){
        return players.stream().filter(
                player -> player.getPosition().equals("Defence") || player.getPosition().equals("Goaltender")).mapToInt(
                player -> (player.getRating() * 2) * player.getPlayerForm() * getTimeOnIce(player)).sum();
    }

    private void raiseDefensivePlayerForm(Player player){
        if (player.getPosition().equals("Defence") || player.getPosition().equals("Goaltender")){
            if (player.getPlayerForm() < 5){
                player.setPlayerForm(player.getPlayerForm() + 1);
            }
        }
    }

    private void raiseAttackingPlayerForm(Player player){
        if (player.getPosition().equals("Center") || player.getPosition().equals("Left Wing") || player.getPosition().equals("Right Wing")){
            if (player.getPlayerForm() < 5){
                player.setPlayerForm(player.getPlayerForm() + 1);
            }
        }
    }

    private void lowerDefensivePlayerForm(Player player){
        if (player.getPosition().equals("Defence") || player.getPosition().equals("Goaltender")){
            if (player.getPlayerForm() > 1){
                player.setPlayerForm(player.getPlayerForm() - 1);
            }
        }
    }

    private void lowerAttackingPlayerForm(Player player){
        if (player.getPosition().equals("Center") || player.getPosition().equals("Left Wing") || player.getPosition().equals("Right Wing")){
            if (player.getPlayerForm() > 1){
                player.setPlayerForm(player.getPlayerForm() - 1);
            }
        }
    }

    private void raiseDefensiveLineForm(ArrayList<Player> players){
            ArrayList<Integer> potentialLine = new ArrayList<>();
            int min = 1;
            int max;
            Random randLine = new Random();
            for (int n= 1; n<3; n++){
                if (n == 1){
                    potentialLine.add(1);
                }
                if (n == 2){
                    max = 2;
                    int line = randLine.nextInt((max - min) +1) + min;
                    potentialLine.add(line);
                }
                else{
                    max = 3;
                    int line = randLine.nextInt((max - min) +1) + min;
                    potentialLine.add(line);
                }
            }
            Random line = new Random();
            potentialLine.add(1);
            players.forEach(player -> {
                if (player.getLine() == potentialLine.get(line.nextInt(potentialLine.size()))){
                    raiseDefensivePlayerForm(player);
                }
            });
    }
}
