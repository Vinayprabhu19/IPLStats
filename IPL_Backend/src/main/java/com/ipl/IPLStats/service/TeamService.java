package com.ipl.IPLStats.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import static org.springframework.util.StringUtils.capitalize;

import com.ipl.IPLStats.Beans.BattingInfo;
import com.ipl.IPLStats.Beans.BowlingInfo;
import com.ipl.IPLStats.Beans.StadiumInfo;
import com.ipl.IPLStats.Beans.TeamInfo;
import com.ipl.IPLStats.repository.PlayerRepository;
import com.ipl.IPLStats.repository.TeamRepository;

import antlr.StringUtils;

@Service
public class TeamService {

	@Autowired
	private TeamRepository teamRepository;

	public ResponseEntity<String> getTeamInfo(String team) {
		JSONObject teamInfo = new JSONObject();
		JSONArray teamsArray=new JSONArray();
		JSONArray venueArray=new JSONArray();
		int totalMatches= teamRepository.getTotalMatches(team);
		int winMatchesTotal = teamRepository.getWinMatches(team);
		int tossWinMatchWin = teamRepository.getTossWinMatchWin(team);
		int tossWinMatchLose = teamRepository.getTossWinMatchLose(team);
		int tossLoseMatchLose = teamRepository.getTossLoseMatchLose(team);
		int tossLoseMatchWin = teamRepository.getTossLoseMatchWin(team);
		teamInfo.put("totalMatches", totalMatches);
		teamInfo.put("winMatchesTotal", winMatchesTotal);
		teamInfo.put("winPercentage", round((float)winMatchesTotal*100/totalMatches,2));
		teamInfo.put("tossWinMatchWin", tossWinMatchWin);
		teamInfo.put("tossWinMatchWinPercentage", round((float)tossWinMatchWin*100/totalMatches,2));
		teamInfo.put("tossWinMatchLose", tossWinMatchLose);
		teamInfo.put("tossWinMatchLosePercentage", round((float)tossWinMatchLose*100/totalMatches,2));
		teamInfo.put("tossLoseMatchLose", tossLoseMatchLose);
		teamInfo.put("tossLoseMatchLosePercentage", round((float)tossLoseMatchLose*100/totalMatches,2));
		teamInfo.put("tossLoseMatchWin", tossLoseMatchWin);
		teamInfo.put("tossLoseMatchWinPercentage", round((float)tossLoseMatchWin*100/totalMatches,2));
		
		List<TeamInfo> teamAllMatches = teamRepository.getTotalMatchesTeam(team);
		for(TeamInfo t:teamAllMatches) {
			JSONObject obj = new JSONObject();
			obj.put("TeamName", t.getTeam());
			obj.put("TotalMatches", t.getTotalMatches());
			teamsArray.put(obj);
		}
		List<TeamInfo> winMatches=teamRepository.getWinMatchesTeam(team);
		for(int i=0;i<winMatches.size();i++) {
			TeamInfo winMatch = winMatches.get(i) ;
			for(int j=0;j<teamsArray.length();j++) {
				JSONObject t = teamsArray.getJSONObject(j);
				if(winMatch.getTeam().equals(t.getString("TeamName"))) {
					t.put("Win", winMatch.getTotalMatches());
					t.put("WinPerc", round((float)winMatch.getTotalMatches() * 100/t.getInt("TotalMatches"),2));
					teamsArray.put(i, t);
					break;
				}
			}
			
		}
		
		List<TeamInfo> venueAllMatches = teamRepository.getTotalMatchesVenue(team);
		for(TeamInfo t:venueAllMatches) {
			JSONObject obj = new JSONObject();
			obj.put("venue", t.getVenue());
			obj.put("TotalMatches", t.getTotalMatches());
			obj.put("Win", 0);
			obj.put("WinPerc", 0);
			venueArray.put(obj);
		}
		List<TeamInfo> venueWinMatches=teamRepository.getWinMatchesVenue(team);
		
		for(int i=0;i<venueWinMatches.size();i++) {
			TeamInfo venue= venueWinMatches.get(i);
			for(int j=0;j<venueArray.length();j++) {
				JSONObject t = venueArray.getJSONObject(j);
				if(t.getString("venue").equals(venue.getVenue())) {
					
					t.put("Win", venue.getTotalMatches());
					t.put("WinPerc", round((float)venue.getTotalMatches() * 100/t.getInt("TotalMatches"),2));
					venueArray.put(j, t);
					break;
				}
			}
			
			
		}
		
		JSONArray playerInfo = new JSONArray();
		List<BattingInfo> teamPlayers= teamRepository.getPlayersInTeam(team);
		for(int i=0;i<teamPlayers.size();i++) {
			JSONObject obj = new JSONObject();
			obj.put("name", teamPlayers.get(i).getPlayer());
			playerInfo.put(obj);
		}
		
		List<BattingInfo> teamBattingInfo=teamRepository.getBatsmanInfo(team);
		List<BowlingInfo> teamBowlingInfo=teamRepository.getBowlerInfo(team);
		for(int i=0;i<teamBattingInfo.size();i++) {
			BattingInfo b = teamBattingInfo.get(i);
			for(int j=0;j<playerInfo.length();j++) {
				JSONObject player = playerInfo.getJSONObject(j);
				if(player.getString("name").equals(b.getPlayer())) {
					player.put("runs", b.getRuns());
					player.put("balls", b.getBalls());
					player.put("strike_rate", round((float)b.getRuns()*100/b.getBalls(),2));
					playerInfo.put(j,player);
					break;
				}
			}
		}
		
		for(int i=0;i<teamBowlingInfo.size();i++) {
			BowlingInfo b = teamBowlingInfo.get(i);
			for(int j=0;j<playerInfo.length();j++) {
				JSONObject player = playerInfo.getJSONObject(j);
				if(player.getString("name").equals(b.getBowler())) {
					int wicketsCount = b.getWickets();
					int runsConceded=b.getRunsConceded();
					int ballsCount=b.getBalls();
					player.put("wickets", b.getWickets());
					player.put("ballsBowled", b.getBalls());
					float averageBowling = wicketsCount>0?(float) runsConceded / wicketsCount :0;
					float strike_rate=wicketsCount>0?(float) ballsCount /wicketsCount :0;
					player.put("strikerateBowling", round(strike_rate,2));
					player.put("averageBowling", round(averageBowling,2));
					playerInfo.put(j,player);
					break;
				}
			}
		}
		
		
		teamInfo.put("playerInfo", playerInfo);
		teamInfo.put("venuesTable", venueArray);
		teamInfo.put("teamsTable", teamsArray);
		return new ResponseEntity<String>(teamInfo.toString(), HttpStatus.OK);
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
