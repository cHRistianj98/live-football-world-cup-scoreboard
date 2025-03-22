package com.christianj98.data;

import lombok.Getter;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public final class FootballMatch {

    private static final int INITIAL_SCORE = 0;

    private final String homeTeamName;
    private final String awayTeamName;
    private final UUID matchId;
    private final LocalDateTime creationDateTime;
    private int homeTeamScore;
    private int awayTeamScore;

    public FootballMatch(String homeTeamName, String awayTeamName, Clock clock) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.matchId = UUID.randomUUID();
        this.creationDateTime = LocalDateTime.now(clock);
        this.homeTeamScore = INITIAL_SCORE;
        this.awayTeamScore = INITIAL_SCORE;
    }

    public FootballMatch(String homeTeamName, String awayTeamName) {
        this(homeTeamName, awayTeamName, Clock.systemDefaultZone());
    }

    public void updateScore(int homeTeamScore, int awayTeamScore) {
        if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new IllegalArgumentException(
                    String.format("Team score cannot be negative. One of the following values is negative: %d %d", homeTeamScore, awayTeamScore));
        }
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }
}