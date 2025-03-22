package com.christianj98.logic.implementation;

import com.christianj98.utils.TestUtils;
import com.christianj98.data.FootballMatch;
import com.christianj98.enumeration.Country;
import com.christianj98.logic.Scoreboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

public class ScoreboardImplTest {

    private Scoreboard cut;

    @BeforeEach
    public void init() {
        cut = new ScoreboardImpl();
    }

    @Nested
    @DisplayName("startFootballMatch(String homeTeam, String awayTeam)")
    class StartFootballMatchMethodTests {

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

    @Nested
    @DisplayName("updateScore(UUID matchId, int homeScore, int awayScore)")
    class UpdateScoreMethodTests {

        @ParameterizedTest
        @MethodSource("provideValidHomeTeamScoreAndAwayTeamScore")
        public void shouldUpdateScoreSuccessfully(int homeScore, int awayScore) {
            // given
            final UUID footballMatchId = cut.startFootballMatch(Country.URUGUAY.getName(), Country.FRANCE.getName());

            // when
            cut.updateScore(footballMatchId, homeScore, awayScore);

            // then
            final var footballMatch = cut.getFootballMatch(footballMatchId);
            assertThat(tuple(footballMatch.getHomeTeamScore(), footballMatch.getAwayTeamScore()))
                    .isEqualTo(tuple(homeScore, awayScore));
        }

        private static Stream<Arguments> provideValidHomeTeamScoreAndAwayTeamScore() {
            return TestUtils.provideValidHomeTeamScoreAndAwayTeamScore();
        }

        @ParameterizedTest
        @MethodSource("provideInvalidHomeTeamScoreOrAwayTeamScore")
        public void shouldThrowIllegalArgumentExceptionWhenScoresAreInvalid(int homeScore, int awayScore) {
            // given
            final UUID footballMatchId = cut.startFootballMatch(Country.URUGUAY.getName(), Country.FRANCE.getName());

            // when / then
            assertThatThrownBy(() -> cut.updateScore(footballMatchId, homeScore, awayScore))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(Integer.toString(homeScore))
                    .hasMessageContaining(Integer.toString(awayScore));
        }

        private static Stream<Arguments> provideInvalidHomeTeamScoreOrAwayTeamScore() {
            return TestUtils.provideInvalidHomeTeamScoreOrAwayTeamScore();
        }

        @Test
        public void shouldThrowNoSuchElementExceptionWhenMatchNotStarted() {
            // given
            final var matchId = UUID.randomUUID();

            // when / then
            assertThatThrownBy(() -> cut.updateScore(matchId, 1, 1))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessageContaining(matchId.toString());
        }
    }

    @Nested
    @DisplayName("finishFootballMatch(UUID matchId)")
    class FinishFootballMatchMethodTests {

        @Test
        public void shouldFinishFootballMatchSuccessfully() {
            // given
            final UUID footballMatchId = cut.startFootballMatch(Country.ARGENTINA.getName(), Country.BRAZIL.getName());

            // when
            cut.finishFootballMatch(footballMatchId);

            // then
            final FootballMatch footballMatch = cut.getFootballMatch(footballMatchId);
            assertThat(footballMatch).isNull();
        }

        @Test
        public void shouldThrowNoSuchElementExceptionWhenMatchHasNotBeenStarted() {
            // given
            final var footballMatchId = UUID.randomUUID();

            // when / then
            assertThatThrownBy(() -> cut.finishFootballMatch(footballMatchId))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessageContaining(footballMatchId.toString());
        }

    }
}
