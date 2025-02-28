package fr.eni.encheres.bll.categorie;

import java.util.List;
import java.util.Optional;

import fr.eni.encheres.exceptions.CategoryAlreadyExistsException;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.categorie.CategorieRepository;

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
	public void update(Categorie categorie) {
		categorieRepo.update(categorie);
	}

	@Override
	public void add(Categorie categorie) {
		categorieRepo.add(categorie);
	}

	@Override
	public void save(Categorie categorie) throws CategoryAlreadyExistsException {

		this.checkIfCategoryAlreadyExist(categorie);

		if(categorie.getNoCategorie() != 0){
			this.update(categorie);
		} else {
			this.add(categorie);
		}
	}

	@Override
	public void delete(int id) {
		categorieRepo.delete(id);
	}

	private void checkIfCategoryAlreadyExist(Categorie categorie) throws CategoryAlreadyExistsException {
		Optional<Categorie> categoryToCompare = categorieRepo.findByLibelle(categorie.getLibelle());
		if(categoryToCompare.isPresent() && categoryToCompare.get().getNoCategorie() != categorie.getNoCategorie()){
			throw new CategoryAlreadyExistsException();
		}
	}
}
