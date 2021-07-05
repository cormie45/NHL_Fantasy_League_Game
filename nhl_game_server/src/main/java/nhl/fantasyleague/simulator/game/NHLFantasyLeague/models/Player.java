package nhl.fantasyleague.simulator.game.NHLFantasyLeague.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "players")

public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "position")
    private String position;

    @Column(name = "line")
    private int line;

    @Column(name = "rating")
    private int rating;

    @Column(name = "player_form")
    private int playerForm;

    @Column(name = "player_value")
    private int playerValue;

    @Column(name = "points")
    private int points;

    @ManyToOne
    @JsonIgnoreProperties({"players"})
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @JsonIgnoreProperties(value = "player")
    @OneToMany(mappedBy = "player")
    private List<Goal> goals;

    public Player(String firstName, String lastName, int age, String position, int line, int rating, int playerValue, Team team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.position = position;
        this.line = line;
        this.rating = rating;
        this.playerValue = playerValue;
        this.playerForm = 3;
        this.points = 0;
        this.team = team;
        this.goals = new ArrayList<>();
    }

    public Player() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPlayerForm() {
        return playerForm;
    }

    public void setPlayerForm(int playerForm) {
        this.playerForm = playerForm;
    }

    public int getPlayerValue() {
        return playerValue;
    }

    public void setPlayerValue(int playerValue) {
        this.playerValue = playerValue;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }
}
