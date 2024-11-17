package com.skyspaceoneoneone.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspaceoneoneone.domain.Reward;
import com.skyspaceoneoneone.dto.RewardDTO;
import com.skyspaceoneoneone.dto.RewardSearchDTO;
import com.skyspaceoneoneone.dto.RewardPageDTO;
import com.skyspaceoneoneone.dto.RewardConvertCriteriaDTO;
import com.skyspaceoneoneone.service.GenericService;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface RewardService extends GenericService<Reward, Integer> {

	List<Reward> findAll();

	ResultDTO addReward(RewardDTO rewardDTO, RequestDTO requestDTO);

	ResultDTO updateReward(RewardDTO rewardDTO, RequestDTO requestDTO);

    Page<Reward> getAllRewards(Pageable pageable);

    Page<Reward> getAllRewards(Specification<Reward> spec, Pageable pageable);

	ResponseEntity<RewardPageDTO> getRewards(RewardSearchDTO rewardSearchDTO);
	
	List<RewardDTO> convertRewardsToRewardDTOs(List<Reward> rewards, RewardConvertCriteriaDTO convertCriteria);

	RewardDTO getRewardDTOById(Integer rewardId);







}





