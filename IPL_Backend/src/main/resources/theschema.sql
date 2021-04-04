CREATE TABLE SCORES(
id INTEGER PRIMARY KEY,
match_id INTEGER,
inning INTEGER,
over INTEGER,
ball INTEGER,
batsman VARCHAR,
non_striker VARCHAR,
bowler VARCHAR,
batsman_runs INTEGER,
extra_runs INTEGER,
total_runs INTEGER,
non_boundary INTEGER,
is_wicket INTEGER,
dismissal_kind VARCHAR,
player_dismissed VARCHAR,
fielder VARCHAR,
extras_type VARCHAR,
batting_team VARCHAR,
bowling_team VARCHAR
);

CREATE TABLE MATCH(
 id INTEGER PRIMARY KEY,
 city VARCHAR,
 date DATE,
 player_of_match VARCHAR,
 venue VARCHAR,
 neutral_venue INT,
 team1 VARCHAR,
 team2 VARCHAR,
 toss_winner VARCHAR,
 toss_decision VARCHAR,
 winner VARCHAR,
 result VARCHAR,
 result_margin INT,
 eliminator VARCHAR,
 method VARCHAR,
 umpire1 VARCHAR,
 umpire2 VARCHAR
 );
 
 CREATE TABLE PLAYER(
	player_name VARCHAR PRIMARY KEY,
	team_name VARCHAR
	);
	
CREATE TABLE TEAM(
  team_name VARCHAR PRIMARY KEY
  );
  
CREATE TABLE GROUND(
 ground_name VARCHAR PRIMARY KEY
 );