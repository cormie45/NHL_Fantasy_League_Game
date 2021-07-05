package nhl.fantasyleague.simulator.game.NHLFantasyLeague.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "games")

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

    @Column(name = "played1st")
    private boolean played1st;

    @Column(name = "home_goals_2nd")
    private int homeGoals2nd;

    @Column(name = "away_goals_2nd")
    private int awayGoals2nd;

    @Column(name = "played2nd")
    private boolean played2nd;

    @Column(name = "home_goals_3rd")
    private int homeGoals3rd;

    @Column(name = "away_goals_3rd")
    private int awayGoals3rd;

    @Column(name = "played3rd")
    private boolean played3rd;

    @Column(name = "total_home")
    private int totalHome;

    @Column(name = "total_away")
    private int totalAway;

    public Game(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals1st = 0;
        this.awayGoals1st = 0;
        this.played1st = false;
        this.homeGoals2nd = 0;
        this.awayGoals2nd = 0;
        this.played2nd = false;
        this.homeGoals3rd = 0;
        this.awayGoals3rd = 0;
        this.played3rd = false;
        this.totalHome = 0;
        this.totalAway = 0;
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

    public boolean hasPlayed3rd() {
        return played3rd;
    }

    public void setPlayed3rd(boolean played3rd) {
        this.played3rd = played3rd;
    }

    public int getTotalHome() {
        return totalHome;
    }

    public void setTotalHome(int totalHome) {
        this.totalHome = totalHome;
    }

    public int getTotalAway() {
        return totalAway;
    }

    public void setTotalAway(int totalAway) {
        this.totalAway = totalAway;
    }
}
