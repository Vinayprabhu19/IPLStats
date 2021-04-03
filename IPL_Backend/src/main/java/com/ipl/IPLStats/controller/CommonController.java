package com.ipl.IPLStats.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ipl.IPLStats.model.Ground;
import com.ipl.IPLStats.model.Player;
import com.ipl.IPLStats.model.Team;
import com.ipl.IPLStats.repository.GroundRepository;
import com.ipl.IPLStats.repository.PlayerRepository;
import com.ipl.IPLStats.repository.TeamRepository;

@RestController
public class CommonController {

	@Autowired
	private GroundRepository groundRepository;
	

	@Autowired
	private PlayerRepository playerRepository;
	

	@Autowired
	private TeamRepository teamRepository;
	
	@RequestMapping(value="/getItems",method = RequestMethod.GET)
	public ResponseEntity<String> getItems(){
		JSONArray items= new JSONArray();
		for(Team t :  teamRepository.findAll()) {
			JSONObject obj = new JSONObject();
			obj.put("name", t.getTeam_name());
			obj.put("type", "Team");
			items.put(obj);
		}
		for(Player p :  playerRepository.findAll()) {
			JSONObject obj = new JSONObject();
			obj.put("name", p.getPlayer_name() );
			obj.put("type", "Player");
			items.put(obj);
		}
		for(Ground g :  groundRepository.findAll()) {
			JSONObject obj = new JSONObject();
			obj.put("name", g.getGround_name());
			obj.put("type", "Ground");
			items.put(obj);
		}
		return new ResponseEntity<>(items.toString(),HttpStatus.OK);
	}
}
