package com.christianj98;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class FootballMatchTest {

    private static final int INITIAL_SCORE = 0;

    @ParameterizedTest
    @MethodSource("provideHomeTeamAndAwayTeam")
    public void shouldCreateValidFootballMatchObject(String homeTeam, String awayTeam) {
        // given
        final var fixedDateTime = LocalDateTime.of(2024, 3, 22, 13, 0, 0);
        final var fixedClock = Clock.fixed(fixedDateTime.toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));

        // when
        final var footballMatch = new FootballMatch(homeTeam, awayTeam, fixedClock);

        // then
        assertThat(footballMatch.getCreationDateTime()).isEqualTo(fixedDateTime);
        assertThat(footballMatch.getMatchId()).isNotNull();
        assertThat(
                tuple(footballMatch.getHomeTeamName(),
                        footballMatch.getAwayTeamName(),
                        footballMatch.getHomeTeamScore(),
                        footballMatch.getAwayTeamScore())
        )
                .isEqualTo(tuple(
                        homeTeam,
                        awayTeam,
                        INITIAL_SCORE,
                        INITIAL_SCORE
                ));
    }


    private static Stream<Arguments> provideHomeTeamAndAwayTeam() {
        return Stream.of(
                Arguments.of(Country.MEXICO.getName(), Country.CANADA.getName()),
                Arguments.of(Country.SPAIN.getName(), Country.BRAZIL.getName()),
                Arguments.of(Country.GERMANY.getName(), Country.FRANCE.getName()),
                Arguments.of(Country.URUGUAY.getName(), Country.ITALY.getName()),
                Arguments.of(Country.ARGENTINA.getName(), Country.AUSTRALIA.getName())
        );
    }
}
