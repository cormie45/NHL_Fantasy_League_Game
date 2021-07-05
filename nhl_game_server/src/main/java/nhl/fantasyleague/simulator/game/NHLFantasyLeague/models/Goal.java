package nhl.fantasyleague.simulator.game.NHLFantasyLeague.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"goals"})
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne
    @JsonIgnoreProperties({"goals"})
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "period")
    private int period;

    public Goal(Player player, Game game, int period) {
        this.player = player;
        this.game = game;
        this.period = period;
    }

    public Goal() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
