package com.ipl.IPLStats.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ipl.IPLStats.model.Player;
import com.ipl.IPLStats.model.Team;
import com.ipl.IPLStats.repository.PlayerRepository;
import com.ipl.IPLStats.repository.TeamRepository;
import com.ipl.IPLStats.service.PlayerService;


@RestController
public class PlayerController {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private PlayerService playerService;
	
	@RequestMapping(value="/getPlayers",method = RequestMethod.GET)
	public ResponseEntity<List> getTeams(){
		List<String> players = new ArrayList<>();
		for(Player p :  playerRepository.findAll()) {
			players.add(p.getPlayer_name());
		}
		return new ResponseEntity<>(players,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getPlayerInfo/{player}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getPlayerInfo(@PathVariable String player){
		return playerService.getPlayerInfo(player);
		
	}
}
