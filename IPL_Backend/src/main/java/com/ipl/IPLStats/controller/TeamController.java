package com.ipl.IPLStats.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ipl.IPLStats.model.Team;
import com.ipl.IPLStats.repository.TeamRepository;
import com.ipl.IPLStats.service.TeamService;

@RestController
public class TeamController {

	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private TeamService teamService;

	@RequestMapping(value = "/getTeams", method = RequestMethod.GET)
	public ResponseEntity<List> getTeams() {
		List<String> teams = new ArrayList<>();
		for (Team t : teamRepository.findAll()) {
			teams.add(t.getTeam_name());
		}
		return new ResponseEntity<>(teams, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getTeamInfo/{team}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getPlayerInfo(@PathVariable String team){
		return teamService.getTeamInfo(team);
		
	}
}
