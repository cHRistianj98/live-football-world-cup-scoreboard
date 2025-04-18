package com.christianj98.utils;

import com.christianj98.enumeration.Country;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public final class TestUtils {

    private TestUtils() {
    }

    public static Stream<Arguments> provideValidHomeTeamAndAwayTeam() {
        return Stream.of(
                Arguments.of(Country.MEXICO.getName(), Country.CANADA.getName()),
                Arguments.of(Country.SPAIN.getName(), Country.BRAZIL.getName()),
                Arguments.of(Country.GERMANY.getName(), Country.FRANCE.getName()),
                Arguments.of(Country.URUGUAY.getName(), Country.ITALY.getName()),
                Arguments.of(Country.ARGENTINA.getName(), Country.AUSTRALIA.getName())
        );
    }

    public static Stream<Arguments> provideInvalidHomeTeamOrAwayTeam() {
        return Stream.of(
                Arguments.of(null, Country.CANADA.getName()),
                Arguments.of(Country.SPAIN.getName(), null),
                Arguments.of(StringUtils.SPACE, Country.FRANCE.getName()),
                Arguments.of(Country.URUGUAY.getName(), StringUtils.EMPTY),
                Arguments.of(null, null)
        );
    }

    public static Stream<Arguments> provideValidHomeTeamScoreAndAwayTeamScore() {
        return Stream.of(
                Arguments.of(1, 0),
                Arguments.of(0, 0),
                Arguments.of(2137, 3),
                Arguments.of(Integer.MAX_VALUE, 3)
        );
    }

    public static Stream<Arguments> provideInvalidHomeTeamScoreOrAwayTeamScore() {
        return Stream.of(
                Arguments.of(-1, 0),
                Arguments.of(3, -3),
                Arguments.of(-3, -12),
                Arguments.of(Integer.MIN_VALUE, 3)
        );
    }
}
