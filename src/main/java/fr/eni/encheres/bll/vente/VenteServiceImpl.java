package fr.eni.encheres.bll.vente;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.vente.VenteRepository;
import fr.eni.encheres.dal.retrait.RetraitRepository;

@Service
public class VenteServiceImpl implements VenteService {

	private final VenteRepository venteRepo;

    //Constructor
	public VenteServiceImpl(VenteRepository venteRepo, RetraitRepository retraitRepo) {
		this.venteRepo = venteRepo;
    }
	
	@Override
	public void update(ArticleVendu entity) {
		venteRepo.update(entity);
	}

	@Override
	public void add(ArticleVendu entity) {
		venteRepo.add(entity);
	}
	
	@Override
	public void delete(int noArticle) {
		venteRepo.delete(noArticle);
	}
	
	@Override
	public void save(ArticleVendu vente) {
		//TODO condition sur l'ajout ou la modification
		this.add(vente);
	}
	
	@Override
	public List<ArticleVendu> findAll() {
		return venteRepo.findAll();
	}

	@Override
	public Optional<ArticleVendu> findById(int noArticle) {
		return venteRepo.findById(noArticle);
	}

	@Override
	public List<ArticleVendu> findByFilter(String searchWordFilter, int noCategory) {
		if(searchWordFilter.isEmpty() && noCategory == -1){
			// Cas ou on recherche sans avoir rempli le champ de saisie et qu'on a mis "Toutes" dans les catégories
			return this.findAll();
		} else if(searchWordFilter.isEmpty()){
			// Cas ou on recherche sans avoir rempli le champ de saisie et qu'on a mis autre chose que "Toutes" dans les catégories
			return this.findByCategory(noCategory);
		} else if(noCategory == -1){
			// Cas ou on recherche en ayant rempli le champ de saisie et qu'on a mis "Toutes" dans les catégories
			return this.findBySearchText(searchWordFilter);
		} else {
			// Cas ou on recherche en ayant rempli le champ de saisie et qu'on a mis autre chose que "Toutes" dans les catégories
			return this.findBySearchTextAndCategory(searchWordFilter, noCategory);
		}
	}

	@Override
	public List<ArticleVendu> findByCategory(int noCategory) {
		return venteRepo.findByCategory(noCategory);
	}

	@Override
	public List<ArticleVendu> findBySearchText(String searchWordFilter) {
		return venteRepo.findBySearchText(searchWordFilter);
	}

	@Override
	public List<ArticleVendu> findBySearchTextAndCategory(String searchWordFilter, int noCategory) {
		return venteRepo.findBySearchTextAndCategory(searchWordFilter, noCategory);
	}


	@Override
	public void encherir(ArticleVendu article, int Montant/*,Utilisateur utilisateur*/) { // TODO USER
		venteRepo.encherir(article, Montant);
	}
	
	@Override
	public String finEnchere(ArticleVendu article,Utilisateur user) { // TODO USER
		return venteRepo.finEnchere(article,user);
	}
}
