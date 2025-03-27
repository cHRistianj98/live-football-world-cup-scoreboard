package com.christianj98.logic;

import com.christianj98.data.FootballMatch;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Scoreboard {
    UUID startFootballMatch(String homeTeam, String awayTeam);
    Optional<FootballMatch> getFootballMatch(UUID matchId);
    void updateScore(UUID matchId, int homeScore, int awayScore);
    void finishFootballMatch(UUID matchId);
    List<FootballMatch> getSummary();
    int getScore(String teamName);
}
