package fr.eni.encheres.dal;

import java.util.List;
import java.util.Optional;

public interface ICrudRepository<T> {
	 
	 List<T> findAll();
	 
	 Optional<T> findById(int id);
	 
	 void add (T entity);
	 
	 void update(T entity);
	 
	 void delete(int id);
}
	 