package com.christianj98;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class ScoreboardImpl implements Scoreboard {

    @Override
    public UUID startFootballMatch(String homeTeam, String awayTeam) {
        if (StringUtils.isBlank(homeTeam) || StringUtils.isBlank(awayTeam)) {
            throw new IllegalArgumentException("Team names cannot be null or blank!");
        }

        return null;
    }

    public FootballMatch getFootballMatch(UUID matchId) {
        return null;
    }
}
