package fr.eni.encheres.bll;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.dal.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepo;
		
	//Constructor
	public ArticleServiceImpl(ArticleRepository articleRepo) {
		this.articleRepo = articleRepo;
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



}
