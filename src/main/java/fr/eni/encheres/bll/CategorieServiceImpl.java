package fr.eni.encheres.bll;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieRepository;

@Service
public class CategorieServiceImpl implements CategorieService {

	private CategorieRepository categorieRepo;
		
	public CategorieServiceImpl(CategorieRepository categorieRepo) {
		this.categorieRepo = categorieRepo;
	}

	@Override
	public List<Categorie> findAll() {
		return categorieRepo.findAll();
	}

	@Override
	public Optional<Categorie> findById(int id) {
		return categorieRepo.findById(id);
	}

	@Override
	public void update(Categorie entity) {
		categorieRepo.update(entity);
	}

	@Override
	public void add(Categorie entity) {
		categorieRepo.add(entity);
	}

	@Override
	public void save(Categorie entity) {
		//TODO faire conditions
		this.add(entity);
	}

	@Override
	public void delete(int id) {
		categorieRepo.delete(id);
	}

}
