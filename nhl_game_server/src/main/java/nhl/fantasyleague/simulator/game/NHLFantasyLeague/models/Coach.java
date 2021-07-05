package nhl.fantasyleague.simulator.game.NHLFantasyLeague.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "coaches")

public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "style")
    private String style;

    @Column(name = "A.I.")
    private boolean ai;

    @OneToOne
    @JsonIgnoreProperties({"coach"})
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    public Coach(String firstName, String lastName, String style, boolean ai, Team team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.style = style;
        this.ai = ai;
        this.team = team;
    }

    public Coach() {

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

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public boolean isAi() {
        return ai;
    }

    public void setAi(boolean ai) {
        this.ai = ai;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
