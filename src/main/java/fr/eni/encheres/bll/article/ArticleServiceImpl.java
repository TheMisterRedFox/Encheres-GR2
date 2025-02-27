package fr.eni.encheres.bll.article;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.article.ArticleRepository;
import fr.eni.encheres.dal.retrait.RetraitRepository;

@Service
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepo;
	private final RetraitRepository retraitRepo;
		
	//Constructor
	public ArticleServiceImpl(ArticleRepository articleRepo, RetraitRepository retraitRepo) {
		this.articleRepo = articleRepo;
		this.retraitRepo = retraitRepo;
	}	
	
	@Override
	public void update(ArticleVendu entity) {
		articleRepo.update(entity);
	}

	@Override
	public void add(ArticleVendu entity) {
		articleRepo.add(entity);
	}
	
	@Override
	public void delete(int noArticle) {
		articleRepo.delete(noArticle);
	}
	
	@Override
	public void save(ArticleVendu article) {
		//TODO condition
		this.add(article);
	}
	
	@Override
	public List<ArticleVendu> findAll() {
		return articleRepo.findAll();
	}

	@Override
	public Optional<ArticleVendu> findById(int noArticle) {
		return articleRepo.findById(noArticle);
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
		return articleRepo.findByCategory(noCategory);
	}

	@Override
	public List<ArticleVendu> findBySearchText(String searchWordFilter) {
		return articleRepo.findBySearchText(searchWordFilter);
	}

	@Override
	public List<ArticleVendu> findBySearchTextAndCategory(String searchWordFilter, int noCategory) {
		return articleRepo.findBySearchTextAndCategory(searchWordFilter, noCategory);
	}


	@Override
	public void encherir(ArticleVendu article, int Montant/*,Utilisateur utilisateur*/) { // TODO USER
		articleRepo.encherir(article, Montant);
		
	}
}
