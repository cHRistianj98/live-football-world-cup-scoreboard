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
- For ease of development, I will use the humble CI at Github Actions.

## License
No license required.