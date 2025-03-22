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

## Prerequisites
- Java 17 or above

## Development notes
- As I am the only one who implements, I will be pushing changes directly to the branch master.
- For ease of development, I will use the humble CI at GitHub Actions.
- To minimise the amount of boilerplate code I decided to use the Lombok library.
- Testing is planned mainly using the AssertJ and JUnit5 libraries.
- I created an enum Country to simplify testing. In reality, club names would be taken, for example, from a database.
- I assume that the code is single-threaded, I am not using multi-threaded collections, synchronisation mechanisms, etc.
- I could have done a spy to the getFootballMatch method but I didn't want to because the implementation with the map is simple enough to test the functionality without unnecessary mocks.
- I chose UUID as the football match id so that I could freely generate a new id using a static class. I could also use int, but then I would have to create some kind of unique number generator.

## License
No license required.