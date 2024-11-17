package com.skyspaceoneoneone.dao;

import java.util.List;

import com.skyspaceoneoneone.dao.GenericDAO;
import com.skyspaceoneoneone.domain.Program;





public interface ProgramDAO extends GenericDAO<Program, Integer> {
  
	List<Program> findAll();
	






}


