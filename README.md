[![Build Status](https://github.com/christianj98/live-football-world-cup-scoreboard/actions/workflows/ci.yml/badge.svg)](https://github.com/christianj98/live-football-world-cup-scoreboard/actions/workflows/ci.yml)

# Live Football World Cup Scoreboard

A simple in-memory scoreboard library to track live football matches and their scores.

## Task requirements
- Start a new match, assuming initial score 0 – 0 and adding it the scoreboard.
  This should capture following parameters:
  - Home team
  - Away team
- Update score. This should receive a pair of absolute scores:
  - home team score
  - away team score
- Finish match currently in progress. This removes a match from the scoreboard.
- Get a summary of matches in progress ordered by their total score. The matches with the
  same total score will be returned ordered by the most recently started match in the
  scoreboard.

## Installation & Requirements
- Java 17 or higher is recommended

### Installation steps:
- Clone or download this repository
- Open it in your favorite IDE or build via Maven
- Make sure you have Java 17 installed
- (Optional) Run the tests (mvn test or similar) to verify everything works

## Key Features

- Start a new football match 
  `UUID startFootballMatch(String homeTeam, String awayTeam)`  
  Creates a new match with a unique ID, storing home/away team names and initial score 0–0.

- Update score
  `void updateScore(UUID matchId, int homeScore, int awayScore)`  
  Updates the score of the specified match. Throws an exception if the match id does not exist.

- Finish a football match
  `void finishFootballMatch(UUID matchId)`  
  Ends the match and removes it from the scoreboard. Throws an exception if the match id does not exist.

- Get summary
  `List<FootballMatch> getSummary()`  
  Returns a list of active matches, sorted so that:
  - Football matches with higher combined scores appear first.
  - Among matches with the same total score, the most recently started one appears higher in the list.

## How It Works
- Uses a data structure (e.g. a `Map<UUID, Match>`) to store ongoing matches.
- Each football match is identified by an UUID `matchId`.
- Scores can be updated or finished at any time using the `matchId`.
- Once a match is finished, it is removed from the scoreboard and no longer appears in the summary.

## Development notes
- As I am the only one who implements, I will be pushing changes directly to the branch master.
- For ease of development, I will use the humble CI at GitHub Actions.
- To minimise the amount of boilerplate code I decided to use the Lombok library.
- Testing is planned mainly using the AssertJ and JUnit5 libraries.
- I created an enum Country to simplify testing. In reality, club names would be taken, for example, from a database.
- I assume that the code is single-threaded, I am not using multi-threaded collections, synchronisation mechanisms, etc.
- I could have done a spy to the getFootballMatch method but I didn't want to because the implementation with the map is simple enough to test the functionality without unnecessary mocks.
- I chose UUID as the football match id so that I could freely generate a new id using a static class. I could also use int, but then I would have to create some kind of unique number generator.
- I was considering whether to implement validation that a team can only play 1 match at a time, but theoretically a team can play at the same time with different lineups so I skipped this case.

## License
No license required.