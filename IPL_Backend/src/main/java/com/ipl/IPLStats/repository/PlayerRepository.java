package com.ipl.IPLStats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ipl.IPLStats.Beans.BattingInfo;
import com.ipl.IPLStats.Beans.BowlingInfo;
import com.ipl.IPLStats.Beans.StadiumInfo;
import com.ipl.IPLStats.model.Player;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

	@Query("select sum(s.batsman_runs) as runs,Count(distinct s.match_id) as innings,count(s.batsman) as balls  from scores as s"
			+ " where s.batsman=?1 and s.extras_type in ('noballs','NA')")
	public BattingInfo getIndividualScores(String playerName);
	
	@Query("select batsman_runs as runType,count(batsman) as runs  from scores "
			+ " where batsman=?1 and extras_type in ('noballs','NA') "
			+ " group by batsman_runs")
	public List<BattingInfo> getRunTypeCount(String playerName);
	
	@Query("select sum(is_wicket) as wickets,bowler as bowler from scores "
			+ "where batsman=?1 and extras_type in ('noballs','NA')"
			+ " group by bowler"
			+ " order by sum(is_wicket) desc")
	public List<BowlingInfo> getBowlingOpponents(String playerName);
	
	@Query("select dismissal_kind as dismissalType,count(*) as dismissalCount "
			+ "from scores "
			+ "where player_dismissed=?1 "
			+ "group by dismissal_kind "
			+ "order by count(*) desc")
	public List<BattingInfo> getDismissalInfo(String playerName);
	
	@Query("select sum(s.batsman_runs) as runs, m.venue as stadium from scores as s inner join match as m on "
			+ "s.match_id=m.id "
			+ "where s.batsman=?1 "
			+ "group by m.venue "
			+ "order by sum(s.batsman_runs) desc")
	public List<StadiumInfo> getStadiumRuns(String playerName);
	
	@Query("select sum(is_wicket) as wickets,count(distinct match_id) as innings , count(bowler) as balls , sum(batsman_runs) as runsConceded from scores "
			+ "where dismissal_kind!='runout' and bowler=?1")
	public BowlingInfo getBowlingInfo(String playerName);
	
	@Query(nativeQuery = true,value = "\r\n"
			+ "select count(wicketsCount) as wickets,wicketsCount as wicketType from ( select sum(is_wicket) as wicketsCount from scores where bowler=?1 group by match_id) as wicketsInfo where wicketsCount!=0 group by wicketsCount")
	public List<BowlingInfo> getWicketsInfo(String playerInfo);
	
	@Query("select  sum(s.is_wicket) as wickets,m.venue as stadium from scores as s inner join match as m on "
			+ "s.match_id=m.id "
			+ "where s.bowler=?1 "
			+ "group by m.venue "
			+ "having sum(s.is_wicket)>0 order by sum(is_wicket) desc")
	public List<StadiumInfo> getStadiumWickets(String playerName);
	
	@Query("select dismissal_kind as dismissalKind,count(dismissal_kind) as dismissalCount from scores\r\n"
			+ "where fielder=?1 \r\n"
			+ "group by dismissal_kind\r\n"
			+ "order by count(dismissal_kind) desc")
	public List<BowlingInfo> getFieldingInfo(String playerName);
}
