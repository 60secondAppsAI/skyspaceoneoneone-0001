package com.skyspaceoneoneone.dao;

import java.util.List;

import com.skyspaceoneoneone.dao.GenericDAO;
import com.skyspaceoneoneone.domain.Lounge;





public interface LoungeDAO extends GenericDAO<Lounge, Integer> {
  
	List<Lounge> findAll();
	






}


