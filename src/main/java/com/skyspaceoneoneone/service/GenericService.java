package com.skyspaceoneoneone.service;

import com.skyspaceoneoneone.dao.GenericDAO;

public interface GenericService<T, ID> {

    abstract GenericDAO<T, ID> getDAO();

    T getById(Integer id) ;

}