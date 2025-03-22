package com.christianj98.logic.implementation;

import com.christianj98.data.FootballMatch;
import com.christianj98.logic.Scoreboard;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class ScoreboardImpl implements Scoreboard {

    private final Map<UUID, FootballMatch> activeFootballMatches = new HashMap<>();

    @Override
    public UUID startFootballMatch(String homeTeam, String awayTeam) {
        if (StringUtils.isBlank(homeTeam) || StringUtils.isBlank(awayTeam)) {
            throw new IllegalArgumentException("Team names cannot be null or blank!");
        }

        final var footballMatch = new FootballMatch(homeTeam, awayTeam);
        activeFootballMatches.put(footballMatch.getMatchId(), footballMatch);
        return footballMatch.getMatchId();
    }

    @Override
    public FootballMatch getFootballMatch(UUID matchId) {
        return activeFootballMatches.get(matchId);
    }

    @Override
    public void updateScore(UUID matchId, int homeScore, int awayScore) {
        final FootballMatch footballMatch = Optional.ofNullable(getFootballMatch(matchId))
                .orElseThrow(() -> new NoSuchElementException(String.format("Football match with id %s does not exist", matchId.toString())));
        footballMatch.updateScore(homeScore, awayScore);
    }
}
