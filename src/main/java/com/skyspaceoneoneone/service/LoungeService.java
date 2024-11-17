package com.skyspaceoneoneone.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspaceoneoneone.domain.Lounge;
import com.skyspaceoneoneone.dto.LoungeDTO;
import com.skyspaceoneoneone.dto.LoungeSearchDTO;
import com.skyspaceoneoneone.dto.LoungePageDTO;
import com.skyspaceoneoneone.dto.LoungeConvertCriteriaDTO;
import com.skyspaceoneoneone.service.GenericService;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface LoungeService extends GenericService<Lounge, Integer> {

	List<Lounge> findAll();

	ResultDTO addLounge(LoungeDTO loungeDTO, RequestDTO requestDTO);

	ResultDTO updateLounge(LoungeDTO loungeDTO, RequestDTO requestDTO);

    Page<Lounge> getAllLounges(Pageable pageable);

    Page<Lounge> getAllLounges(Specification<Lounge> spec, Pageable pageable);

	ResponseEntity<LoungePageDTO> getLounges(LoungeSearchDTO loungeSearchDTO);
	
	List<LoungeDTO> convertLoungesToLoungeDTOs(List<Lounge> lounges, LoungeConvertCriteriaDTO convertCriteria);

	LoungeDTO getLoungeDTOById(Integer loungeId);







}





