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
import com.ipl.IPLStats.repository.PlayerRepository;

import antlr.StringUtils;

@Service
public class PlayerService {

	@Autowired
	private PlayerRepository playerRepository;

	public ResponseEntity<String> getPlayerInfo(String player) {
		JSONObject playerInfo = new JSONObject();
		JSONObject battingRecord = this.getPlayerBattingInfo(player);
		JSONObject bowlingRecord = this.getPlayerBowlingInfo(player);
		playerInfo.put("batting", battingRecord);
		playerInfo.put("bowling", bowlingRecord);
		return new ResponseEntity<String>(playerInfo.toString(), HttpStatus.OK);
	}

	public JSONObject getPlayerBowlingInfo(String player) {
		JSONObject playerBowlingInfo=new JSONObject();
		BowlingInfo info = playerRepository.getBowlingInfo(player);
		playerBowlingInfo.put("name", player);
		
		int wicketsCount = info.getWickets()!=null?info.getWickets():0;
		int inningsCount = info.getInnings()!=null?info.getInnings():0;
		int ballsCount = info.getBalls()!=null?info.getBalls():0;
		float averageBowling = inningsCount>0?(float) wicketsCount / inningsCount:0;
		float strike_rate=ballsCount>0?(float) wicketsCount* 100 / ballsCount:0;
		playerBowlingInfo.put("wickets", wicketsCount);
		playerBowlingInfo.put("balls", ballsCount);
		playerBowlingInfo.put("innings", inningsCount);
		playerBowlingInfo.put("average", round(averageBowling, 2));
		playerBowlingInfo.put("strike_rate", round(strike_rate, 2));
		List<StadiumInfo> stadiumWickets = playerRepository.getStadiumWickets(player);
		List<BowlingInfo> wicketsInfo=playerRepository.getWicketsInfo(player);
		JSONArray wicketsArray = new JSONArray();
		for (BowlingInfo r : wicketsInfo) {
			if(r.getWicketType().equals("0"))
				continue;
			JSONObject obj = new JSONObject();
			obj.put("key", r.getWicketType()+" Wickets");
			obj.put("value", r.getWickets());
			wicketsArray.put(obj);
		}
		JSONArray stadiumWicketsArray = new JSONArray();
		int count = 0;
		for (StadiumInfo s : stadiumWickets) {
			if (count++ > 5)
				break;
			JSONObject obj = new JSONObject();
			obj.put("key", s.getStadium());
			obj.put("value", s.getWickets()+"W");
			stadiumWicketsArray.put(obj);
		}
		JSONObject favoriteStadium=new JSONObject();
		if (stadiumWicketsArray.length() > 0) {
			favoriteStadium.put("key", stadiumWicketsArray.getJSONObject(0).get("value"));
			favoriteStadium.put("value", stadiumWicketsArray.getJSONObject(0).get("key"));
			stadiumWicketsArray.remove(0);
			
			playerBowlingInfo.put("favoriteStadium",favoriteStadium);
		}
		else {
			favoriteStadium.put("key", "");
			favoriteStadium.put("value", "");
			playerBowlingInfo.put("favoriteStadium",favoriteStadium);
		}
		List<BowlingInfo> fieldingInfo = playerRepository.getFieldingInfo(player);
		
		JSONArray fieldingArray = new JSONArray();
		for (BowlingInfo f : fieldingInfo) {

			JSONObject obj = new JSONObject();
			obj.put("key", capitalize(f.getDismissalKind()));
			obj.put("value",f.getDismissalCount());
			fieldingArray.put(obj);
		}
		if (fieldingArray.length() > 0)
			playerBowlingInfo.put("topFielding", fieldingArray.get(0));
		else {
			JSONObject topFielding= new JSONObject();
			topFielding.put("key", "");
			topFielding.put("value", "");
			playerBowlingInfo.put("topFielding", topFielding);
		}
		playerBowlingInfo.put("fieldingInfo", fieldingArray);
		JSONArray stats=new JSONArray();
		JSONObject innings=new JSONObject();
		innings.put("key", "Innings");
		innings.put("value", info.getInnings());
		JSONObject wickets=new JSONObject();
		wickets.put("key", "Wickets");
		wickets.put("value", info.getWickets());
		JSONObject balls=new JSONObject();
		balls.put("key", "Balls");
		balls.put("value", info.getBalls());
		JSONObject average=new JSONObject();
		average.put("key", "Average");
		average.put("value", round(averageBowling, 2));
		JSONObject strikeRate=new JSONObject();
		strikeRate.put("key", "Strike Rate");
		strikeRate.put("value", round(strike_rate, 2));
		stats.put(innings);
		stats.put(wickets);
		stats.put(balls);
		stats.put(average);
		stats.put(strikeRate);
		playerBowlingInfo.put("stats", stats);
		playerBowlingInfo.put("stadiumWickets", stadiumWicketsArray);
		playerBowlingInfo.put("wicketsInfo", wicketsArray);
		return playerBowlingInfo;
	}

	public JSONObject getPlayerBattingInfo(String player) {
		BattingInfo info = playerRepository.getIndividualScores(player);
		List<BattingInfo> runTypeInfo = playerRepository.getRunTypeCount(player);
		List<BowlingInfo> BowlingInfos = playerRepository.getBowlingOpponents(player);
		List<BattingInfo> dismissalInfo = playerRepository.getDismissalInfo(player);
		List<StadiumInfo> stadiumRuns = playerRepository.getStadiumRuns(player);
		JSONObject playerBattingInfo = new JSONObject();
		JSONArray runTypeArray = new JSONArray();
		for (BattingInfo r : runTypeInfo) {
			JSONObject obj = new JSONObject();
			obj.put("key", r.getRunType()+"s");
			obj.put("value", r.getRuns());
			runTypeArray.put(obj);
		}
		JSONArray dismissalArray = new JSONArray();
		for (BattingInfo d : dismissalInfo) {
			JSONObject obj = new JSONObject();
			obj.put("key", capitalize(d.getDismissalType()));
			obj.put("value", d.getDismissalCount()+" times");
			dismissalArray.put(obj);
		}
		JSONArray opponentInfo = new JSONArray();
		int count = 0;
		for (BowlingInfo b : BowlingInfos) {
			if (count++ > 5 || b.getWickets() == 0)
				break;
			JSONObject obj = new JSONObject();
			obj.put("key", b.getBowler());
			obj.put("value", b.getWickets()+" W");
			opponentInfo.put(obj);
		}
		JSONArray stadiumRunsArray = new JSONArray();
		count = 0;
		for (StadiumInfo s : stadiumRuns) {
			if (count++ > 3)
				break;
			JSONObject obj = new JSONObject();
			obj.put("key", s.getStadium());
			obj.put("value", s.getRuns()+" Runs");
			stadiumRunsArray.put(obj);
		}
		JSONObject favoriteStadium=new JSONObject();
		JSONObject strongestOpponent=new JSONObject();
		JSONObject frequentDismissalType=new JSONObject();
		if (dismissalArray.length() > 0) {
			frequentDismissalType.put("key", dismissalArray.getJSONObject(0).get("value"));
			frequentDismissalType.put("value", dismissalArray.getJSONObject(0).get("key"));
			dismissalArray.remove(0);
			
			playerBattingInfo.put("frequentDismissalType",frequentDismissalType);
		}
		else {
			frequentDismissalType.put("key", "");
			frequentDismissalType.put("value", "");
			playerBattingInfo.put("frequentDismissalType",frequentDismissalType);
		}
		if (stadiumRunsArray.length() > 0) {
			favoriteStadium.put("key", stadiumRunsArray.getJSONObject(0).get("value"));
			favoriteStadium.put("value", stadiumRunsArray.getJSONObject(0).get("key"));
			stadiumRunsArray.remove(0);
			
			playerBattingInfo.put("favoriteStadium",favoriteStadium);
		}
		else {
			favoriteStadium.put("key", "");
			favoriteStadium.put("value", "");
			playerBattingInfo.put("favoriteStadium",favoriteStadium);
		}
		if (opponentInfo.length() > 0)
		{
			strongestOpponent.put("key", opponentInfo.getJSONObject(0).get("value"));
			strongestOpponent.put("value", opponentInfo.getJSONObject(0).get("key"));
			opponentInfo.remove(0);
			
			playerBattingInfo.put("strongestOpponent",strongestOpponent);
		}
		else {
			strongestOpponent.put("key", "");
			strongestOpponent.put("value", "");
			playerBattingInfo.put("strongestOpponent",strongestOpponent);
		}
		int runsCount = info.getRuns()!=null?info.getRuns():0;
		int inningsCount = info.getInnings()!=null?info.getInnings():0;
		int ballsCount = info.getBalls()!=null?info.getBalls():0;
		float averageBatting = inningsCount>0?(float) runsCount / inningsCount:0;
		float strike_rate=ballsCount>0?(float) runsCount* 100 / ballsCount:0;
		JSONArray stats=new JSONArray();
		JSONObject innings=new JSONObject();
		innings.put("key", "Innings");
		innings.put("value", inningsCount);
		JSONObject runs=new JSONObject();
		runs.put("key", "Runs");
		runs.put("value", runsCount);
		JSONObject balls=new JSONObject();
		balls.put("key", "Balls");
		balls.put("value", ballsCount);
		JSONObject average=new JSONObject();
		average.put("key", "Average");
		average.put("value", round(averageBatting, 2));
		JSONObject strikeRate=new JSONObject();
		strikeRate.put("key", "Strike Rate");
		strikeRate.put("value", round(strike_rate, 2));
		stats.put(innings);
		stats.put(runs);
		stats.put(balls);
		stats.put(average);
		stats.put(strikeRate);
		playerBattingInfo.put("name", player);
		playerBattingInfo.put("stats", stats);
		playerBattingInfo.put("runs", runsCount);
		playerBattingInfo.put("balls", ballsCount);
		playerBattingInfo.put("innings", inningsCount);
		playerBattingInfo.put("average", round(averageBatting, 2));
		playerBattingInfo.put("strike_rate", round(strike_rate, 2));
		playerBattingInfo.put("stadiumRuns", stadiumRunsArray);
		playerBattingInfo.put("dismissalInfo", dismissalArray);
		playerBattingInfo.put("opponentInfo", opponentInfo);
		playerBattingInfo.put("runType", runTypeArray);
		return playerBattingInfo;
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
