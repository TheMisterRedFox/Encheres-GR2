package fr.eni.encheres.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Retrait {

	@Size(max = 250, message = "Le nom de la rue ne doit pas dépasser 250 caractères")
	@NotBlank
	private String rue;

	@Size(max = 15, message = "Le code postal ne doit pas dépasser 15 caractères")
	@NotBlank
	private String codePostal;

	@Size(max = 250, message = "Le nom de la ville ne doit pas dépasser 250 caractères")
	@NotBlank
	private String ville;

	@NotNull
	private ArticleVendu article;
	
	public Retrait() {}
	
	public Retrait(String rue, String codePostal, String ville, ArticleVendu article) {
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.article = article;
	}
	
	public Retrait(String rue, String codePostal, String ville) {
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public ArticleVendu getArticle() {
		return article;
	}

	public void setArticle(ArticleVendu article) {
		this.article = article;
	}

	@Override
	public String toString() {
		return rue + " - " + ville + " " + codePostal ;
	}
		
	
}
