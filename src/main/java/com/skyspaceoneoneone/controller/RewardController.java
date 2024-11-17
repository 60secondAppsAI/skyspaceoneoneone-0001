package com.skyspaceoneoneone.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.skyspaceoneoneone.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.skyspaceoneoneone.domain.Reward;
import com.skyspaceoneoneone.dto.RewardDTO;
import com.skyspaceoneoneone.dto.RewardSearchDTO;
import com.skyspaceoneoneone.dto.RewardPageDTO;
import com.skyspaceoneoneone.service.RewardService;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/reward")
@RestController
public class RewardController {

	private final static Logger logger = LoggerFactory.getLogger(RewardController.class);

	@Autowired
	RewardService rewardService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Reward> getAll() {

		List<Reward> rewards = rewardService.findAll();
		
		return rewards;	
	}

	@GetMapping(value = "/{rewardId}")
	@ResponseBody
	public RewardDTO getReward(@PathVariable Integer rewardId) {
		
		return (rewardService.getRewardDTOById(rewardId));
	}

 	@RequestMapping(value = "/addReward", method = RequestMethod.POST)
	public ResponseEntity<?> addReward(@RequestBody RewardDTO rewardDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = rewardService.addReward(rewardDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/rewards")
	public ResponseEntity<RewardPageDTO> getRewards(RewardSearchDTO rewardSearchDTO) {
 
		return rewardService.getRewards(rewardSearchDTO);
	}	

	@RequestMapping(value = "/updateReward", method = RequestMethod.POST)
	public ResponseEntity<?> updateReward(@RequestBody RewardDTO rewardDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = rewardService.updateReward(rewardDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
