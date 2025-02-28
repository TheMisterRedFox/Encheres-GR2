package fr.eni.encheres.bll.categorie;

import java.util.List;
import java.util.Optional;

import fr.eni.encheres.bll.vente.VenteService;
import fr.eni.encheres.exceptions.CategoryAlreadyExistsException;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.categorie.CategorieRepository;

@Service
public class CategorieServiceImpl implements CategorieService {

	private CategorieRepository categorieRepo;
	private VenteService venteService;
		
	public CategorieServiceImpl(CategorieRepository categorieRepo, VenteService venteService) {
		this.categorieRepo = categorieRepo;
		this.venteService = venteService;
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
	public void delete(int id) throws CategoryAlreadyExistsException {

		if(venteService.findByCategory(id).isEmpty()){
			categorieRepo.delete(id);
		} else {
			throw new CategoryAlreadyExistsException();
		}

	}

	private void checkIfCategoryAlreadyExist(Categorie categorie) throws CategoryAlreadyExistsException {
		Optional<Categorie> categoryToCompare = categorieRepo.findByLibelle(categorie.getLibelle());
		if(categoryToCompare.isPresent() && categoryToCompare.get().getNoCategorie() != categorie.getNoCategorie()){
			throw new CategoryAlreadyExistsException();
		}
	}
}
