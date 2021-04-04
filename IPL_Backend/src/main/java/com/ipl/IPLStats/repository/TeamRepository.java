package com.ipl.IPLStats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ipl.IPLStats.Beans.BattingInfo;
import com.ipl.IPLStats.Beans.BowlingInfo;
import com.ipl.IPLStats.Beans.TeamInfo;
import com.ipl.IPLStats.model.Team;

public interface TeamRepository extends CrudRepository<Team, Integer> {

	@Query("select count(*) from match inner join team as t on "
			+ "t.team_name=team1 or t.team_name=team2 "
			+ "where t.team_name=?1 "
			+ "and toss_winner=?1 "
			+ "and winner=?1")
	public Integer getTossWinMatchWin(String team);
	
	@Query("select count(*) from match inner join team as t on "
			+ "t.team_name=team1 or t.team_name=team2 "
			+ "where t.team_name=?1 "
			+ "and toss_winner=?1 "
			+ "and winner!=?1")
	public Integer getTossWinMatchLose(String team);
	
	@Query("select count(*) from match inner join team as t on "
			+ "t.team_name=team1 or t.team_name=team2 "
			+ "where t.team_name=?1 "
			+ "and toss_winner!=?1 "
			+ "and winner=?1")
	public Integer getTossLoseMatchWin(String team);
	
	@Query("select count(*) from match inner join team as t on "
			+ "t.team_name=team1 or t.team_name=team2 "
			+ "where t.team_name=?1 "
			+ "and toss_winner!=?1 "
			+ "and winner!=?1")
	public Integer getTossLoseMatchLose(String team);
	
	@Query("select count(*) from match inner join team as t on "
			+ "t.team_name=team1 or t.team_name=team2 "
			+ "where t.team_name=?1 ")
	public Integer getTotalMatches(String team);
	
	@Query("select count(*) from match inner join team as t on "
			+ "t.team_name=team1 or t.team_name=team2 "
			+ "where t.team_name=?1 and winner=?1")
	public Integer getWinMatches(String team);
	
	
	@Query("select t.team_name as team,count(m.winner) as totalMatches from match as m inner join team as t on\r\n"
			+ "t.team_name=team1 or t.team_name=team2\r\n"
			+ "where (team1=?1 or team2=?1)\r\n"
			+ "and team_name!=?1\r\n"
			+ "group by team_name\r\n"
			+ "order by team_name")
	public List<TeamInfo> getTotalMatchesTeam(String team);
	
	
	@Query("select t.team_name as team,count(m.winner) as totalMatches from match as m inner join team as t on\r\n"
			+ "t.team_name=team1 or t.team_name=team2\r\n"
			+ "where (team1=?1 or team2=?1)\r\n"
			+ "and team_name!=?1 and winner=?1\r\n"
			+ "group by team_name\r\n"
			+ "order by team_name")
	public List<TeamInfo> getWinMatchesTeam(String team);
	
	@Query("select m.venue as venue,count(m.winner) as totalMatches from match as m inner join team as t on\r\n"
			+ "t.team_name=team1 or t.team_name=team2\r\n"
			+ "where (team1=?1 or team2=?1)\r\n"
			+ "and team_name!=?1\r\n"
			+ "group by m.venue\r\n"
			+ "order by m.venue")
	public List<TeamInfo> getTotalMatchesVenue(String team);
	
	
	@Query("select m.venue as venue,count(m.winner) as totalMatches from match as m inner join team as t on\r\n"
			+ "t.team_name=team1 or t.team_name=team2\r\n"
			+ "where (team1=?1 or team2=?1)\r\n"
			+ "and team_name!=?1 and winner=?1\r\n"
			+ "group by m.venue\r\n"
			+ "order by m.venue")
	public List<TeamInfo> getWinMatchesVenue(String team);
	
	@Query("select player_name as player from player \r\n"
			+ "where team_name=?1\r\n"
			+ "order by player_name")
	public List<BattingInfo> getPlayersInTeam(String team);
	
	@Query("select s.batsman as player,sum(s.batsman_runs) as runs,count(*) as balls from scores as s inner join player as p\r\n"
			+ "on s.batsman=p.player_name\r\n"
			+ "where p.team_name=?1 \r\n"
			+ "group by s.batsman")
	public List<BattingInfo> getBatsmanInfo(String team);
	
	@Query("select s.bowler as bowler,sum(s.is_wicket) as wickets,count(*) as balls,sum(s.total_runs) as runsConceded from scores as s inner join player as p\r\n"
			+ "on s.bowler=p.player_name\r\n"
			+ "where p.team_name=?1 and s.dismissal_kind!='runout'\r\n"
			+ "group by s.bowler\r\n"
			+ "order by s.bowler")
	
	public List<BowlingInfo> getBowlerInfo(String team);
	
}
