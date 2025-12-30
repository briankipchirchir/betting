package com.kopa.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "predictions")
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String league;

    @Column(name = "`match`")
    private String match;

    @Column(name = "match_date")
    private LocalDate matchDate;

    @Column(name = "match_time")
    private LocalTime matchTime;

    private String odds;
    private String prediction;
    private boolean active;
    private boolean vip;

    // Constructors
    public Prediction() {}

    public Prediction(String league, String match, LocalDate matchDate,
                      LocalTime matchTime, String odds, String prediction,
                      boolean active, boolean vip) {
        this.league = league;
        this.match = match;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.odds = odds;
        this.prediction = prediction;
        this.active = active;
        this.vip = vip;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public String getLeague() { return league; }
    public void setLeague(String league) { this.league = league; }

    public String getMatch() { return match; }
    public void setMatch(String match) { this.match = match; }

    public LocalDate getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDate matchDate) { this.matchDate = matchDate; }

    public LocalTime getMatchTime() { return matchTime; }
    public void setMatchTime(LocalTime matchTime) { this.matchTime = matchTime; }

    public String getOdds() { return odds; }
    public void setOdds(String odds) { this.odds = odds; }

    public String getPrediction() { return prediction; }
    public void setPrediction(String prediction) { this.prediction = prediction; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public boolean isVip() { return vip; }
    public void setVip(boolean vip) { this.vip = vip; }
}
