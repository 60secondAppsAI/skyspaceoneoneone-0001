package com.skyspaceoneoneone.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspaceoneoneone.domain.ServiceRequest;
import com.skyspaceoneoneone.dto.ServiceRequestDTO;
import com.skyspaceoneoneone.dto.ServiceRequestSearchDTO;
import com.skyspaceoneoneone.dto.ServiceRequestPageDTO;
import com.skyspaceoneoneone.dto.ServiceRequestConvertCriteriaDTO;
import com.skyspaceoneoneone.service.GenericService;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ServiceRequestService extends GenericService<ServiceRequest, Integer> {

	List<ServiceRequest> findAll();

	ResultDTO addServiceRequest(ServiceRequestDTO serviceRequestDTO, RequestDTO requestDTO);

	ResultDTO updateServiceRequest(ServiceRequestDTO serviceRequestDTO, RequestDTO requestDTO);

    Page<ServiceRequest> getAllServiceRequests(Pageable pageable);

    Page<ServiceRequest> getAllServiceRequests(Specification<ServiceRequest> spec, Pageable pageable);

	ResponseEntity<ServiceRequestPageDTO> getServiceRequests(ServiceRequestSearchDTO serviceRequestSearchDTO);
	
	List<ServiceRequestDTO> convertServiceRequestsToServiceRequestDTOs(List<ServiceRequest> serviceRequests, ServiceRequestConvertCriteriaDTO convertCriteria);

	ServiceRequestDTO getServiceRequestDTOById(Integer serviceRequestId);







}





