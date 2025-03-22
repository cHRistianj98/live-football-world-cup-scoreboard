package com.christianj98;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

public class ScoreboardImplTest {

    private ScoreboardImpl cut;

    @BeforeEach
    public void init() {
        cut = new ScoreboardImpl();
    }

    @ParameterizedTest
    @MethodSource("provideValidHomeTeamAndAwayTeam")
    public void shouldStartFootballMatch(String homeTeam, String awayTeam) {

        // when
        final UUID footballMatchId = cut.startFootballMatch(homeTeam, awayTeam);
        final FootballMatch footballMatch = cut.getFootballMatch(footballMatchId);

        // then
        assertThat(footballMatchId).isNotNull();
        assertThat(footballMatch).isNotNull();
        assertThat(
                tuple(
                        footballMatch.getHomeTeamName(),
                        footballMatch.getAwayTeamName())
        ).isEqualTo(
                tuple(
                        homeTeam,
                        awayTeam
                ));
    }

    private static Stream<Arguments> provideValidHomeTeamAndAwayTeam() {
        return TestUtils.provideValidHomeTeamAndAwayTeam();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidHomeTeamOrAwayTeam")
    public void shouldThrowExceptionFoInvalidArguments(String homeTeam, String awayTeam) {
        // when / then
        assertThatThrownBy(() -> cut.startFootballMatch(homeTeam, awayTeam))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideInvalidHomeTeamOrAwayTeam() {
        return TestUtils.provideInvalidHomeTeamOrAwayTeam();
    }
}
