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
}
