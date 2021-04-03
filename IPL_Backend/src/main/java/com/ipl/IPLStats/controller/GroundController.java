package com.ipl.IPLStats.controller;

import java.util.ArrayList;
import java.util.List;

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
public class GroundController {

	@Autowired
	private GroundRepository groundRepository;
	
	@RequestMapping(value="/getGrounds",method = RequestMethod.GET)
	public ResponseEntity<List> getTeams(){
		List<String> grounds = new ArrayList<>();
		for(Ground p :  groundRepository.findAll()) {
			grounds.add(p.getGround_name());
		}
		return new ResponseEntity<>(grounds,HttpStatus.OK);
	}
}
