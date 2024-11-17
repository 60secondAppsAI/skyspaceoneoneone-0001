package com.skyspaceoneoneone.dao;

import java.util.List;

import com.skyspaceoneoneone.dao.GenericDAO;
import com.skyspaceoneoneone.domain.Maintenance;





public interface MaintenanceDAO extends GenericDAO<Maintenance, Integer> {
  
	List<Maintenance> findAll();
	






}


