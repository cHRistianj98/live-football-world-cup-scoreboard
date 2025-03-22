package com.christianj98.logic;

import com.christianj98.data.FootballMatch;

import java.util.UUID;

public interface Scoreboard {
    UUID startFootballMatch(String homeTeam, String awayTeam);
    FootballMatch getFootballMatch(UUID matchId);
    void updateScore(UUID matchId, int homeScore, int awayScore);
}
