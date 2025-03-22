package com.christianj98;

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
}
