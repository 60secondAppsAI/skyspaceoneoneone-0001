package com.skyspaceoneoneone.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspaceoneoneone.domain.FrequentFlyer;
import com.skyspaceoneoneone.dto.FrequentFlyerDTO;
import com.skyspaceoneoneone.dto.FrequentFlyerSearchDTO;
import com.skyspaceoneoneone.dto.FrequentFlyerPageDTO;
import com.skyspaceoneoneone.dto.FrequentFlyerConvertCriteriaDTO;
import com.skyspaceoneoneone.service.GenericService;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface FrequentFlyerService extends GenericService<FrequentFlyer, Integer> {

	List<FrequentFlyer> findAll();

	ResultDTO addFrequentFlyer(FrequentFlyerDTO frequentFlyerDTO, RequestDTO requestDTO);

	ResultDTO updateFrequentFlyer(FrequentFlyerDTO frequentFlyerDTO, RequestDTO requestDTO);

    Page<FrequentFlyer> getAllFrequentFlyers(Pageable pageable);

    Page<FrequentFlyer> getAllFrequentFlyers(Specification<FrequentFlyer> spec, Pageable pageable);

	ResponseEntity<FrequentFlyerPageDTO> getFrequentFlyers(FrequentFlyerSearchDTO frequentFlyerSearchDTO);
	
	List<FrequentFlyerDTO> convertFrequentFlyersToFrequentFlyerDTOs(List<FrequentFlyer> frequentFlyers, FrequentFlyerConvertCriteriaDTO convertCriteria);

	FrequentFlyerDTO getFrequentFlyerDTOById(Integer frequentFlyerId);







}





