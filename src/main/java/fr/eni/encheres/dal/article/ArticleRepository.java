package fr.eni.encheres.dal.article;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.ICrudRepository;

public interface ArticleRepository extends ICrudRepository<ArticleVendu> {

	void encherir(int noArticle, Enchere enchere, int Montant);

}
