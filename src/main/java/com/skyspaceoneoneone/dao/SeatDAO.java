package com.skyspaceoneoneone.dao;

import java.util.List;

import com.skyspaceoneoneone.dao.GenericDAO;
import com.skyspaceoneoneone.domain.Seat;





public interface SeatDAO extends GenericDAO<Seat, Integer> {
  
	List<Seat> findAll();
	






}


