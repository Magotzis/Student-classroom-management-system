package com.magotzis.mapper;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<T, ID extends Serializable> {

	int deleteById(ID id);

	int insert(T t);

	int update(T t);
	
	T getById(ID id);

	List<T> list(T t);
	
	List<T> findByPage(T t);
	
}
