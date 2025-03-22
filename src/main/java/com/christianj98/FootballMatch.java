package com.christianj98;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public final class FootballMatch {

    private final String homeTeamName;
    private final String awayTeamName;
    private UUID matchId;
    private LocalDateTime creationDateTime;
    @Setter
    private int homeTeamScore = 0;
    @Setter
    private int awayTeamScore = 0;

    public FootballMatch(String homeTeamName, String awayTeamName, Clock clock) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
    }

    public FootballMatch(String homeTeamName, String awayTeamName) {
        this(homeTeamName, awayTeamName, Clock.systemDefaultZone());
    }
}