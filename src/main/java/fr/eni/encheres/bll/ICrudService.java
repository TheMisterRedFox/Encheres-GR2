package fr.eni.encheres.bll;

import java.util.List;
import java.util.Optional;

public interface ICrudService<T> {
	 
	 List<T> findAll();
	 
	 Optional<T> findById(int id);
	 
	 void update(T entity);
	 
	 void add(T entity);
	 
	 void save(T entity);
	 
	 void delete(int id);
	 
}