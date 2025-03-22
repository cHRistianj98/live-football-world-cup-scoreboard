package com.christianj98;

import com.christianj98.data.FootballMatch;
import com.christianj98.enumeration.Country;
import com.christianj98.logic.Scoreboard;
import com.christianj98.logic.implementation.ScoreboardImpl;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        Scoreboard scoreboard = new ScoreboardImpl();

        final UUID matchMexicoVsCanadaId = startMatchAndUpdateScore(scoreboard, Country.MEXICO.getName(), Country.CANADA.getName(), 0, 5);
        startMatchAndUpdateScore(scoreboard, Country.SPAIN.getName(), Country.BRAZIL.getName(), 10, 2);
        startMatchAndUpdateScore(scoreboard, Country.GERMANY.getName(), Country.FRANCE.getName(), 2, 2);
        final UUID marchUruguayVsItaly = startMatchAndUpdateScore(scoreboard, Country.URUGUAY.getName(), Country.ITALY.getName(), 6, 6);
        startMatchAndUpdateScore(scoreboard, Country.ARGENTINA.getName(), Country.AUSTRALIA.getName(), 3, 1);

        printSummary(scoreboard);

//        System.out.println("Finish Mexico vs Canada match");
//        scoreboard.finishFootballMatch(matchMexicoVsCanadaId);
//        printSummary(scoreboard);

//        System.out.println("Finish Uruguay vs Italy match");
//        scoreboard.finishFootballMatch(marchUruguayVsItaly);
//        printSummary(scoreboard);

//        System.out.println("Finish all matches");
//        scoreboard.getSummary().stream().map(FootballMatch::getMatchId).forEach(scoreboard::finishFootballMatch);
//        printSummary(scoreboard);
    }

    private static void printSummary(Scoreboard scoreboard) {
        System.out.println("Summary:");
        scoreboard.getSummary().forEach(match -> System.out.println(printResult(match)));
        System.out.println();
    }

    private static UUID startMatchAndUpdateScore(Scoreboard scoreboard, String homeTeam, String awayTeam, int homeScore, int awayScore) {
        final UUID matchId = scoreboard.startFootballMatch(homeTeam, awayTeam);
        scoreboard.updateScore(matchId, homeScore, awayScore);
        return matchId;
    }

    private static String printResult(FootballMatch match) {
        return String.format("%s %d : %d %s", match.getHomeTeamName(), match.getHomeTeamScore(), match.getAwayTeamScore(), match.getAwayTeamName());
    }
}
