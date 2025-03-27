package com.christianj98.logic.implementation;

import com.christianj98.exception.TeamNotFoundException;
import com.christianj98.utils.TestUtils;
import com.christianj98.data.FootballMatch;
import com.christianj98.enumeration.Country;
import com.christianj98.logic.Scoreboard;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
            final Optional<FootballMatch> footballMatch = cut.getFootballMatch(footballMatchId);

            // then
            assertThat(footballMatchId).isNotNull();
            assertThat(footballMatch).isNotEmpty();
            assertThat(
                    tuple(
                            footballMatch.get().getHomeTeamName(),
                            footballMatch.get().getAwayTeamName())
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
            final Optional<FootballMatch> footballMatch = cut.getFootballMatch(footballMatchId);
            assertThat(footballMatch).isNotEmpty();
            assertThat(tuple(footballMatch.get().getHomeTeamScore(), footballMatch.get().getAwayTeamScore()))
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
            final Optional<FootballMatch> footballMatch = cut.getFootballMatch(footballMatchId);
            assertThat(footballMatch).isEmpty();
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

    @Nested
    @DisplayName("getSummary()")
    class GetSummaryMethodTests {

        @Test
        public void shouldGetSummaryOfMatchesInProgressOrderedByTheirScore() {
            // given
            final UUID matchMexicoVsCanadaId = startMatchAndUpdateScore(Country.MEXICO.getName(), Country.CANADA.getName(), 0, 5);
            final UUID matchSpainVsBrazilId = startMatchAndUpdateScore(Country.SPAIN.getName(), Country.BRAZIL.getName(), 10, 2);
            final UUID matchGermanyVsFranceId = startMatchAndUpdateScore(Country.GERMANY.getName(), Country.FRANCE.getName(), 2, 2);
            final UUID matchUruguayVsItalyId = startMatchAndUpdateScore(Country.URUGUAY.getName(), Country.ITALY.getName(), 6, 6);
            final UUID matchArgentinaVsAustraliaId = startMatchAndUpdateScore(Country.ARGENTINA.getName(), Country.AUSTRALIA.getName(), 3, 1);

            // when
            final List<FootballMatch> summary = cut.getSummary();

            // then
            assertThat(summary)
                    .extracting(FootballMatch::getMatchId)
                    .containsExactly(
                            matchUruguayVsItalyId,
                            matchSpainVsBrazilId,
                            matchMexicoVsCanadaId,
                            matchArgentinaVsAustraliaId,
                            matchGermanyVsFranceId
                    );
        }
    }

    @Nested
    @DisplayName("getScore()")
    class GetScoreTests {

        @Test
        public void shouldGetHomeScore() {
            // given
            String teamName = Country.ARGENTINA.getName();
            final UUID matchId = cut.startFootballMatch(teamName, Country.BRAZIL.getName());
            cut.updateScore(matchId, 1, 0);
            final int expectedScore = cut.getFootballMatch(matchId).get().getHomeTeamScore();

            // when
            final int result = cut.getScore(teamName);

            // then
            assertThat(result).isEqualTo(expectedScore);
        }

        @Test
        public void shouldGetAwayScore() {
            // given
            final String homeTeamName = Country.ARGENTINA.getName();
            final String awayTeamName = Country.BRAZIL.getName();
            final int expectedScore = 10;
            final UUID matchId = cut.startFootballMatch(homeTeamName, awayTeamName);
            cut.updateScore(matchId, 1, expectedScore);

            // when
            final int result = cut.getScore(awayTeamName);

            // then
            assertThat(result).isEqualTo(expectedScore);
        }

        @Test
        public void shouldFail() {
            // given
            final String teamName = Country.URUGUAY.getName();

            // when / then
            assertThatThrownBy(() -> cut.getScore(teamName))
                    .isInstanceOf(TeamNotFoundException.class);
        }

        @ParameterizedTest
        @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
        @NullSource
        public void shouldHandleAllInvalidTeamNames(String teamName) {
            // when / then
            assertThatThrownBy(() -> cut.getScore(teamName))
                    .isInstanceOf(TeamNotFoundException.class);
        }
    }

    private UUID startMatchAndUpdateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        final UUID matchId = cut.startFootballMatch(homeTeam, awayTeam);
        cut.updateScore(matchId, homeScore, awayScore);
        return matchId;
    }
}
