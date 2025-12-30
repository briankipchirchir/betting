package com.kopa.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "results")
public class Result {

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
    private String pick;
    private String result;

    // Constructors
    public Result() {}

    public Result(String league, String match, LocalDate matchDate,
                  LocalTime matchTime, String odds, String pick, String result) {
        this.league = league;
        this.match = match;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.odds = odds;
        this.pick = pick;
        this.result = result;
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

    public String getPick() { return pick; }
    public void setPick(String pick) { this.pick = pick; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
}
