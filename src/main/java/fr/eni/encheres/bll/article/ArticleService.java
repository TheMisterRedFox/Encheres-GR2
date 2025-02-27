package fr.eni.encheres.bll.article;

import fr.eni.encheres.bll.ICrudService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

public interface ArticleService extends ICrudService<ArticleVendu> {

	void encherir(int noArticle, Enchere enchere, int Montant, Utilisateur utilisateur);

}
