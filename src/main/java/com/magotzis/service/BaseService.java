package com.magotzis.service;

import java.io.Serializable;
import java.util.List;

import com.magotzis.mybatis.page.Page;

public interface BaseService<T, ID extends Serializable> {

	int add(T t);

	int deleteById(ID id);

	int update(T t);

	T getById(ID id);

	List<T> list(T t);
	
	Page<T> findByPage(T t); 
	
	Page<T> buildPage(List<T> data);

	<V>Page<V> buildVoPage(List<V> data);

}
