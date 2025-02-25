package fr.eni.encheres.bo;

import java.time.LocalDateTime;

public class Enchere {
	
	private LocalDateTime dateEnchere;
	private int montantEnchere;
	
	private ArticleVendu article;
	private Utilisateur encherisseur;
	
	private Enchere() {}

	public Enchere(LocalDateTime dateEnchere, int montantEnchere, ArticleVendu article, Utilisateur encherisseur) {
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.article = article;
		this.encherisseur = encherisseur;
	}

	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(LocalDateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public ArticleVendu getArticle() {
		return article;
	}

	public void setArticle(ArticleVendu article) {
		this.article = article;
	}

	public Utilisateur getEncherisseur() {
		return encherisseur;
	}

	public void setEncherisseur(Utilisateur encherisseur) {
		this.encherisseur = encherisseur;
	}

	
	
}
