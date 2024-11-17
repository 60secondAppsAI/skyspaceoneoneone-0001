package com.skyspaceoneoneone.dao;

import java.util.List;

import com.skyspaceoneoneone.dao.GenericDAO;
import com.skyspaceoneoneone.domain.Flight;





public interface FlightDAO extends GenericDAO<Flight, Integer> {
  
	List<Flight> findAll();
	






}


