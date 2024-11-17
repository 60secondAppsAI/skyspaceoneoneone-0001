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
import com.skyspaceoneoneone.dao.ServiceRequestDAO;
import com.skyspaceoneoneone.domain.ServiceRequest;
import com.skyspaceoneoneone.dto.ServiceRequestDTO;
import com.skyspaceoneoneone.dto.ServiceRequestSearchDTO;
import com.skyspaceoneoneone.dto.ServiceRequestPageDTO;
import com.skyspaceoneoneone.dto.ServiceRequestConvertCriteriaDTO;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import com.skyspaceoneoneone.service.ServiceRequestService;
import com.skyspaceoneoneone.util.ControllerUtils;





@Service
public class ServiceRequestServiceImpl extends GenericServiceImpl<ServiceRequest, Integer> implements ServiceRequestService {

    private final static Logger logger = LoggerFactory.getLogger(ServiceRequestServiceImpl.class);

	@Autowired
	ServiceRequestDAO serviceRequestDao;

	


	@Override
	public GenericDAO<ServiceRequest, Integer> getDAO() {
		return (GenericDAO<ServiceRequest, Integer>) serviceRequestDao;
	}
	
	public List<ServiceRequest> findAll () {
		List<ServiceRequest> serviceRequests = serviceRequestDao.findAll();
		
		return serviceRequests;	
		
	}

	public ResultDTO addServiceRequest(ServiceRequestDTO serviceRequestDTO, RequestDTO requestDTO) {

		ServiceRequest serviceRequest = new ServiceRequest();

		serviceRequest.setServiceRequestId(serviceRequestDTO.getServiceRequestId());


		serviceRequest.setDescription(serviceRequestDTO.getDescription());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		serviceRequest = serviceRequestDao.save(serviceRequest);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<ServiceRequest> getAllServiceRequests(Pageable pageable) {
		return serviceRequestDao.findAll(pageable);
	}

	public Page<ServiceRequest> getAllServiceRequests(Specification<ServiceRequest> spec, Pageable pageable) {
		return serviceRequestDao.findAll(spec, pageable);
	}

	public ResponseEntity<ServiceRequestPageDTO> getServiceRequests(ServiceRequestSearchDTO serviceRequestSearchDTO) {
	
			Integer serviceRequestId = serviceRequestSearchDTO.getServiceRequestId(); 
 			String description = serviceRequestSearchDTO.getDescription(); 
 			String sortBy = serviceRequestSearchDTO.getSortBy();
			String sortOrder = serviceRequestSearchDTO.getSortOrder();
			String searchQuery = serviceRequestSearchDTO.getSearchQuery();
			Integer page = serviceRequestSearchDTO.getPage();
			Integer size = serviceRequestSearchDTO.getSize();

	        Specification<ServiceRequest> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, serviceRequestId, "serviceRequestId"); 
			
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

		Page<ServiceRequest> serviceRequests = this.getAllServiceRequests(spec, pageable);
		
		//System.out.println(String.valueOf(serviceRequests.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(serviceRequests.getTotalPages()));
		
		List<ServiceRequest> serviceRequestsList = serviceRequests.getContent();
		
		ServiceRequestConvertCriteriaDTO convertCriteria = new ServiceRequestConvertCriteriaDTO();
		List<ServiceRequestDTO> serviceRequestDTOs = this.convertServiceRequestsToServiceRequestDTOs(serviceRequestsList,convertCriteria);
		
		ServiceRequestPageDTO serviceRequestPageDTO = new ServiceRequestPageDTO();
		serviceRequestPageDTO.setServiceRequests(serviceRequestDTOs);
		serviceRequestPageDTO.setTotalElements(serviceRequests.getTotalElements());
		return ResponseEntity.ok(serviceRequestPageDTO);
	}

	public List<ServiceRequestDTO> convertServiceRequestsToServiceRequestDTOs(List<ServiceRequest> serviceRequests, ServiceRequestConvertCriteriaDTO convertCriteria) {
		
		List<ServiceRequestDTO> serviceRequestDTOs = new ArrayList<ServiceRequestDTO>();
		
		for (ServiceRequest serviceRequest : serviceRequests) {
			serviceRequestDTOs.add(convertServiceRequestToServiceRequestDTO(serviceRequest,convertCriteria));
		}
		
		return serviceRequestDTOs;

	}
	
	public ServiceRequestDTO convertServiceRequestToServiceRequestDTO(ServiceRequest serviceRequest, ServiceRequestConvertCriteriaDTO convertCriteria) {
		
		ServiceRequestDTO serviceRequestDTO = new ServiceRequestDTO();
		
		serviceRequestDTO.setServiceRequestId(serviceRequest.getServiceRequestId());

	
		serviceRequestDTO.setDescription(serviceRequest.getDescription());

	

		
		return serviceRequestDTO;
	}

	public ResultDTO updateServiceRequest(ServiceRequestDTO serviceRequestDTO, RequestDTO requestDTO) {
		
		ServiceRequest serviceRequest = serviceRequestDao.getById(serviceRequestDTO.getServiceRequestId());

		serviceRequest.setServiceRequestId(ControllerUtils.setValue(serviceRequest.getServiceRequestId(), serviceRequestDTO.getServiceRequestId()));

		serviceRequest.setDescription(ControllerUtils.setValue(serviceRequest.getDescription(), serviceRequestDTO.getDescription()));



        serviceRequest = serviceRequestDao.save(serviceRequest);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ServiceRequestDTO getServiceRequestDTOById(Integer serviceRequestId) {
	
		ServiceRequest serviceRequest = serviceRequestDao.getById(serviceRequestId);
			
		
		ServiceRequestConvertCriteriaDTO convertCriteria = new ServiceRequestConvertCriteriaDTO();
		return(this.convertServiceRequestToServiceRequestDTO(serviceRequest,convertCriteria));
	}







}
