package fr.eni.encheres.bll;

import java.util.List;
import java.util.Optional;

public interface ICrudService<T> {
	 
	 List<T> findAll();
	 
	 Optional<T> findById(int id);
	 
	 void save(T t);
	 
	 void delete(int id);
	 
}