Java | Spring | PostreSQL | JavaScript | React


<a href="https://www.repostatus.org/#wip"><img src="https://www.repostatus.org/badges/latest/wip.svg" alt="Project Status: WIP – Initial development is in progress, but there has not yet been a stable, usable release suitable for the public." /></a>

A Project to expand upon the NHL Leage Simulator App turning it into a working fantasy league style game. Users will be able to create their own team and select a starting roster from a large databse of available players. Results are simulated using Team Form, Player Form, Player Quality, and Time on Ice, ensuring that the better a team is playing the greater the chance of winning and vice versa.

Working:
    Database
    Game Logic
    

In Design:
    Front-End UI
    Player Transfer System
    Roster Line Management
    

Instructions:

  1. Fork this repo.
  2. Clone onto your computer.
  3. cd into the directory in your terminal.
  4. Create PostreSQL user role (may not be required) with the 'createuser <username>' command.
  5. Initialise the database with the command 'createdb nhl_fantasy_league_db'.
  6. Open the nhl_game_server project with your prefered Java IDE.
  7. Add your PostgreSQL username and password to the application.properties file.
      spring.datasource.username=<username>
      spring.datasource.password=<password>
  8. Run the NhlFantasyLeagueApplication main file.
  9. Server available via 'http://localhost:8080/api/' in your browser.
  
Available Commands:

  'http://localhost:8080/api/coaches' - View all teams head coaches.
  
  'http://localhost:8080/api/coaches/<id>' - View individual head coach by id number (E.g.  http://localhost:8080/api/coaches/1).
  
  
  'http://localhost:8080/api/teams' - View all teams.
  
  'http://localhost:8080/api/teams/<id>' - View individual team by id number (E.g.  http://localhost:8080/api/teams/1).
  
  
  'http://localhost:8080/api/players' - View all players.
  
  'http://localhost:8080/api/players/<id>' - View individual player by id number (E.g.  http://localhost:8080/api/players/1).
  
  
  'http://localhost:8080/api/games/create' - Sets up league creating home and away fixtures for all teams (Run this first)
  
  'http://localhost:8080/api/games' - View all games.
  
  'http://localhost:8080/api/games/<id>' - View individual games by id number (E.g.  http://localhost:8080/api/games/1).
  
  'http://localhost:8080/api/games/simulate/week' - Simulate all fixtures (1 game per team) to be played per week. Ice hockey games are split into 3 periods, run this command 3    times to finish all games, will continue until no more games are left to play.
  
  'http://localhost:8080/api/games/simulate/<id>' - Play an individual game (x3 to finish the game)
  
  
  'http://localhost:8080/api/goals' - View all goals scored.
  
  'http://localhost:8080/api/goals/<id>' - View individual goal by id number (E.g.  http://localhost:8080/api/goals/1).
