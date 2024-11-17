package com.skyspaceoneoneone.dao;

import java.util.List;

import com.skyspaceoneoneone.dao.GenericDAO;
import com.skyspaceoneoneone.domain.FrequentFlyer;





public interface FrequentFlyerDAO extends GenericDAO<FrequentFlyer, Integer> {
  
	List<FrequentFlyer> findAll();
	






}


