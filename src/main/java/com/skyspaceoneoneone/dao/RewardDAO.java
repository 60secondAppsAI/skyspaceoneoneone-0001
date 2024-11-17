package com.skyspaceoneoneone.dao;

import java.util.List;

import com.skyspaceoneoneone.dao.GenericDAO;
import com.skyspaceoneoneone.domain.Reward;





public interface RewardDAO extends GenericDAO<Reward, Integer> {
  
	List<Reward> findAll();
	






}


