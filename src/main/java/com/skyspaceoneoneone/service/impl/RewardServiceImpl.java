package com.skyspaceoneoneone.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.skyspaceoneoneone.dao.GenericDAO;
import com.skyspaceoneoneone.service.GenericService;
import com.skyspaceoneoneone.service.impl.GenericServiceImpl;
import com.skyspaceoneoneone.dao.RewardDAO;
import com.skyspaceoneoneone.domain.Reward;
import com.skyspaceoneoneone.dto.RewardDTO;
import com.skyspaceoneoneone.dto.RewardSearchDTO;
import com.skyspaceoneoneone.dto.RewardPageDTO;
import com.skyspaceoneoneone.dto.RewardConvertCriteriaDTO;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import com.skyspaceoneoneone.service.RewardService;
import com.skyspaceoneoneone.util.ControllerUtils;





@Service
public class RewardServiceImpl extends GenericServiceImpl<Reward, Integer> implements RewardService {

    private final static Logger logger = LoggerFactory.getLogger(RewardServiceImpl.class);

	@Autowired
	RewardDAO rewardDao;

	


	@Override
	public GenericDAO<Reward, Integer> getDAO() {
		return (GenericDAO<Reward, Integer>) rewardDao;
	}
	
	public List<Reward> findAll () {
		List<Reward> rewards = rewardDao.findAll();
		
		return rewards;	
		
	}

	public ResultDTO addReward(RewardDTO rewardDTO, RequestDTO requestDTO) {

		Reward reward = new Reward();

		reward.setRewardId(rewardDTO.getRewardId());


		reward.setDescription(rewardDTO.getDescription());


		reward.setPointsRequired(rewardDTO.getPointsRequired());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		reward = rewardDao.save(reward);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Reward> getAllRewards(Pageable pageable) {
		return rewardDao.findAll(pageable);
	}

	public Page<Reward> getAllRewards(Specification<Reward> spec, Pageable pageable) {
		return rewardDao.findAll(spec, pageable);
	}

	public ResponseEntity<RewardPageDTO> getRewards(RewardSearchDTO rewardSearchDTO) {
	
			Integer rewardId = rewardSearchDTO.getRewardId(); 
 			String description = rewardSearchDTO.getDescription(); 
  			String sortBy = rewardSearchDTO.getSortBy();
			String sortOrder = rewardSearchDTO.getSortOrder();
			String searchQuery = rewardSearchDTO.getSearchQuery();
			Integer page = rewardSearchDTO.getPage();
			Integer size = rewardSearchDTO.getSize();

	        Specification<Reward> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, rewardId, "rewardId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Reward> rewards = this.getAllRewards(spec, pageable);
		
		//System.out.println(String.valueOf(rewards.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(rewards.getTotalPages()));
		
		List<Reward> rewardsList = rewards.getContent();
		
		RewardConvertCriteriaDTO convertCriteria = new RewardConvertCriteriaDTO();
		List<RewardDTO> rewardDTOs = this.convertRewardsToRewardDTOs(rewardsList,convertCriteria);
		
		RewardPageDTO rewardPageDTO = new RewardPageDTO();
		rewardPageDTO.setRewards(rewardDTOs);
		rewardPageDTO.setTotalElements(rewards.getTotalElements());
		return ResponseEntity.ok(rewardPageDTO);
	}

	public List<RewardDTO> convertRewardsToRewardDTOs(List<Reward> rewards, RewardConvertCriteriaDTO convertCriteria) {
		
		List<RewardDTO> rewardDTOs = new ArrayList<RewardDTO>();
		
		for (Reward reward : rewards) {
			rewardDTOs.add(convertRewardToRewardDTO(reward,convertCriteria));
		}
		
		return rewardDTOs;

	}
	
	public RewardDTO convertRewardToRewardDTO(Reward reward, RewardConvertCriteriaDTO convertCriteria) {
		
		RewardDTO rewardDTO = new RewardDTO();
		
		rewardDTO.setRewardId(reward.getRewardId());

	
		rewardDTO.setDescription(reward.getDescription());

	
		rewardDTO.setPointsRequired(reward.getPointsRequired());

	

		
		return rewardDTO;
	}

	public ResultDTO updateReward(RewardDTO rewardDTO, RequestDTO requestDTO) {
		
		Reward reward = rewardDao.getById(rewardDTO.getRewardId());

		reward.setRewardId(ControllerUtils.setValue(reward.getRewardId(), rewardDTO.getRewardId()));

		reward.setDescription(ControllerUtils.setValue(reward.getDescription(), rewardDTO.getDescription()));

		reward.setPointsRequired(ControllerUtils.setValue(reward.getPointsRequired(), rewardDTO.getPointsRequired()));



        reward = rewardDao.save(reward);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public RewardDTO getRewardDTOById(Integer rewardId) {
	
		Reward reward = rewardDao.getById(rewardId);
			
		
		RewardConvertCriteriaDTO convertCriteria = new RewardConvertCriteriaDTO();
		return(this.convertRewardToRewardDTO(reward,convertCriteria));
	}







}
