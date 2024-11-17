package com.skyspaceoneoneone.dao;

import java.util.List;

import com.skyspaceoneoneone.dao.GenericDAO;
import com.skyspaceoneoneone.domain.Baggage;





public interface BaggageDAO extends GenericDAO<Baggage, Integer> {
  
	List<Baggage> findAll();
	






}


