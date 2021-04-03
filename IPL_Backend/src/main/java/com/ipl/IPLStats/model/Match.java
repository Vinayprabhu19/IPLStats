package com.ipl.IPLStats.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "match")
public class Match {

	@Id
	@Column
	private int id;
	@Column private String city;
	@Column private String date;
	@Column private String player_of_match;
	@Column private String venue;
	@Column private String neutral_venue;
	@Column private String team1;
	@Column private String team2;
	@Column private String toss_winner;
	@Column private String toss_decision;
	@Column private String winner;
	@Column private String result;
	@Column private String result_margin;
	@Column private String eliminator;
	@Column private String method;
	@Column private String umpire1;
	@Column private String umpire2;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPlayer_of_match() {
		return player_of_match;
	}
	public void setPlayer_of_match(String player_of_match) {
		this.player_of_match = player_of_match;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getNeutral_venue() {
		return neutral_venue;
	}
	public void setNeutral_venue(String neutral_venue) {
		this.neutral_venue = neutral_venue;
	}
	public String getTeam1() {
		return team1;
	}
	public void setTeam1(String team1) {
		this.team1 = team1;
	}
	public String getTeam2() {
		return team2;
	}
	public void setTeam2(String team2) {
		this.team2 = team2;
	}
	public String getToss_winner() {
		return toss_winner;
	}
	public void setToss_winner(String toss_winner) {
		this.toss_winner = toss_winner;
	}
	public String getToss_decision() {
		return toss_decision;
	}
	public void setToss_decision(String toss_decision) {
		this.toss_decision = toss_decision;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResult_margin() {
		return result_margin;
	}
	public void setResult_margin(String result_margin) {
		this.result_margin = result_margin;
	}
	public String getEliminator() {
		return eliminator;
	}
	public void setEliminator(String eliminator) {
		this.eliminator = eliminator;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getUmpire1() {
		return umpire1;
	}
	public void setUmpire1(String umpire1) {
		this.umpire1 = umpire1;
	}
	public String getUmpire2() {
		return umpire2;
	}
	public void setUmpire2(String umpire2) {
		this.umpire2 = umpire2;
	}
	 
	 
}
