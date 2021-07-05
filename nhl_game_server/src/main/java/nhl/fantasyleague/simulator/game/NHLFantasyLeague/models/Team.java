package nhl.fantasyleague.simulator.game.NHLFantasyLeague.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")

public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private Long balance;

    @Column(name = "stadium")
    private String stadium;

    @Column(name ="city")
    private String city;

    @Column(name = "games_played")
    private int gamesPlayed;

    @Column(name = "points")
    private int points;

    @Column(name = "potential_points")
    private int potentialPoints;

    @Column(name = "goals_for")
    private int goalsFor;

    @Column(name = "goals_against")
    private int goalsAgainst;

    @Column(name = "team_form")
    private int teamForm;

    @JsonIgnoreProperties(value = "team")
    @OneToMany(mappedBy = "team")
    private List<Player> players;

    @OneToOne(mappedBy = "team", cascade = CascadeType.ALL)
    private Coach coach;

    public Team(String name, Long balance, String stadium, String city) {
        this.name = name;
        this.balance = balance;
        this.stadium = stadium;
        this.city = city;
        this.gamesPlayed = 0;
        this.points = 0;
        this.potentialPoints = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
        this.teamForm = 3;
        this.players = new ArrayList<>();
    }

    public Team() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getPoints() {
        return points;
    }

    public int getPotentialPoints() {
        return potentialPoints;
    }

    public void setPotentialPoints(int potentialPoints) {
        this.potentialPoints = potentialPoints;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getTeamForm() {
        return teamForm;
    }

    public void setTeamForm(int teamForm) {
        this.teamForm = teamForm;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }
}
