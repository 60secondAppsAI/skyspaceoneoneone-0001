package com.skyspaceoneoneone.dao;

import java.util.List;

import com.skyspaceoneoneone.dao.GenericDAO;
import com.skyspaceoneoneone.domain.Airline;





public interface AirlineDAO extends GenericDAO<Airline, Integer> {
  
	List<Airline> findAll();
	






}


