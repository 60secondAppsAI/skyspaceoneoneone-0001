package com.skyspaceoneoneone.dao;

import java.util.List;

import com.skyspaceoneoneone.dao.GenericDAO;
import com.skyspaceoneoneone.domain.Passenger;





public interface PassengerDAO extends GenericDAO<Passenger, Integer> {
  
	List<Passenger> findAll();
	






}


