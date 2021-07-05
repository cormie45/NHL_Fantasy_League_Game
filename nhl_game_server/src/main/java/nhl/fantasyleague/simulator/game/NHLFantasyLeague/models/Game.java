package nhl.fantasyleague.simulator.game.NHLFantasyLeague.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "games")

public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"games", "players", "balance", "stadium", "city", "teamForm"})
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team homeTeam;

    @ManyToOne
    @JsonIgnoreProperties({"games", "players", "balance", "stadium", "city", "teamForm"})
    @JoinColumn(name = "away_team_id", nullable = false)
    private Team awayTeam;

    @Column(name = "home_goals_1st")
    private int homeGoals1st;

    @Column(name = "away_goals_1st")
    private int awayGoals1st;

    @Column(name = "home_scored_1st")
    private List<Player> homeScored1st;

    @Column(name = "away_scored_1st")
    private List<Player> awayScored1st;

    @Column(name = "played1st")
    private boolean played1st;

    @Column(name = "home_goals_2nd")
    private int homeGoals2nd;

    @Column(name = "away_goals_2nd")
    private int awayGoals2nd;

    @Column(name = "home_scored_2nd")
    private List<Player> homeScored2nd;

    @Column(name = "away_scored_2nd")
    private List<Player> awayScored2nd;

    @Column(name = "played2nd")
    private boolean played2nd;

    @Column(name = "home_goals_3rd")
    private int homeGoals3rd;

    @Column(name = "away_goals_3rd")
    private int awayGoals3rd;

    @Column(name = "home_scored_3rd")
    private List<Player> homeScored3rd;

    @Column(name = "away_scored_3rd")
    private List<Player> awayScored3rd;

    @Column(name = "played3rd")
    private boolean played3rd;

    public Game(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals1st = 0;
        this.awayGoals1st = 0;
        this.homeScored1st = new ArrayList<>();
        this.awayScored1st = new ArrayList<>();
        this.played1st = false;
        this.homeGoals2nd = 0;
        this.awayGoals2nd = 0;
        this.homeScored2nd = new ArrayList<>();
        this.awayScored2nd = new ArrayList<>();
        this.played2nd = false;
        this.homeGoals3rd = 0;
        this.awayGoals3rd = 0;
        this.homeScored3rd = new ArrayList<>();
        this.awayScored3rd = new ArrayList<>();
        this.played3rd = false;
    }

    public Game() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getHomeGoals1st() {
        return homeGoals1st;
    }

    public void setHomeGoals1st(int homeGoals1st) {
        this.homeGoals1st = homeGoals1st;
    }

    public int getAwayGoals1st() {
        return awayGoals1st;
    }

    public void setAwayGoals1st(int awayGoals1st) {
        this.awayGoals1st = awayGoals1st;
    }

    public List<Player> getHomeScored1st() {
        return homeScored1st;
    }

    public void setHomeScored1st(List<Player> homeScored1st) {
        this.homeScored1st = homeScored1st;
    }

    public List<Player> getAwayScored1st() {
        return awayScored1st;
    }

    public void setAwayScored1st(List<Player> awayScored1st) {
        this.awayScored1st = awayScored1st;
    }

    public boolean hasPlayed1st() {
        return played1st;
    }

    public void setPlayed1st(boolean played1st) {
        this.played1st = played1st;
    }

    public int getHomeGoals2nd() {
        return homeGoals2nd;
    }

    public void setHomeGoals2nd(int homeGoals2nd) {
        this.homeGoals2nd = homeGoals2nd;
    }

    public int getAwayGoals2nd() {
        return awayGoals2nd;
    }

    public void setAwayGoals2nd(int awayGoals2nd) {
        this.awayGoals2nd = awayGoals2nd;
    }

    public List<Player> getHomeScored2nd() {
        return homeScored2nd;
    }

    public void setHomeScored2nd(List<Player> homeScored2nd) {
        this.homeScored2nd = homeScored2nd;
    }

    public List<Player> getAwayScored2nd() {
        return awayScored2nd;
    }

    public void setAwayScored2nd(List<Player> awayScored2nd) {
        this.awayScored2nd = awayScored2nd;
    }

    public boolean hasPlayed2nd() {
        return played2nd;
    }

    public void setPlayed2nd(boolean played2nd) {
        this.played2nd = played2nd;
    }

    public int getHomeGoals3rd() {
        return homeGoals3rd;
    }

    public void setHomeGoals3rd(int homeGoals3rd) {
        this.homeGoals3rd = homeGoals3rd;
    }

    public int getAwayGoals3rd() {
        return awayGoals3rd;
    }

    public void setAwayGoals3rd(int awayGoals3rd) {
        this.awayGoals3rd = awayGoals3rd;
    }

    public List<Player> getHomeScored3rd() {
        return homeScored3rd;
    }

    public void setHomeScored3rd(List<Player> homeScored3rd) {
        this.homeScored3rd = homeScored3rd;
    }

    public List<Player> getAwayScored3rd() {
        return awayScored3rd;
    }

    public void setAwayScored3rd(List<Player> awayScored3rd) {
        this.awayScored3rd = awayScored3rd;
    }

    public boolean hasPlayed3rd() {
        return played3rd;
    }

    public void setPlayed3rd(boolean played3rd) {
        this.played3rd = played3rd;
    }
}
