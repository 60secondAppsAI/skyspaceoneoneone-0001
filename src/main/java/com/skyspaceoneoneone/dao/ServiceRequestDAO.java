package com.skyspaceoneoneone.dao;

import java.util.List;

import com.skyspaceoneoneone.dao.GenericDAO;
import com.skyspaceoneoneone.domain.ServiceRequest;





public interface ServiceRequestDAO extends GenericDAO<ServiceRequest, Integer> {
  
	List<ServiceRequest> findAll();
	






}


