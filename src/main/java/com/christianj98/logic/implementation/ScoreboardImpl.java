package com.christianj98.logic.implementation;

import com.christianj98.data.FootballMatch;
import com.christianj98.logic.Scoreboard;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
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
    public Optional<FootballMatch> getFootballMatch(UUID matchId) {
        return Optional.ofNullable(activeFootballMatches.get(matchId));
    }

    @Override
    public void updateScore(UUID matchId, int homeScore, int awayScore) {
        final FootballMatch footballMatch = getFootballMatch(matchId)
                .orElseThrow(() -> new NoSuchElementException(getFootballMatchNotExistErrorMessage(matchId)));
        footballMatch.updateScore(homeScore, awayScore);
    }

    @Override
    public void finishFootballMatch(UUID matchId) {
        if (!activeFootballMatches.containsKey(matchId)) {
            throw new NoSuchElementException(getFootballMatchNotExistErrorMessage(matchId));
        }
        activeFootballMatches.remove(matchId);
    }

    @Override
    public List<FootballMatch> getSummary() {
        return activeFootballMatches.values()
                .stream()
                .sorted() // implements Comparable
                .toList();
    }

    private String getFootballMatchNotExistErrorMessage(UUID matchId) {
        return String.format("Football match with id: %s does not exist!", matchId.toString());
    }
}
