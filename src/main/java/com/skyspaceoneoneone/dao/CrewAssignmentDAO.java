package com.skyspaceoneoneone.dao;

import java.util.List;

import com.skyspaceoneoneone.dao.GenericDAO;
import com.skyspaceoneoneone.domain.CrewAssignment;





public interface CrewAssignmentDAO extends GenericDAO<CrewAssignment, Integer> {
  
	List<CrewAssignment> findAll();
	






}


