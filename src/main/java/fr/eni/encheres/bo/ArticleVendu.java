package fr.eni.encheres.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class ArticleVendu {

	private int noArticle;

	@Size(max = 100, message = "Le nom de l'article ne doit pas dépasser 100 caractères")
	@NotBlank
	private String nomArticle;

	@Size(max = 300, message = "La description de l'article ne doit pas dépasser 300 caractères")
	@NotBlank
	private String description;
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime dateDebutEncheres;
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private String etatVente;
	private int meilleureOffre;
	private String pseudoMeilleurAcheteur;
	private boolean archivage;
	private Utilisateur vendeur;
	private Retrait retrait;
	private Categorie categorie;
	private String imageUrl;
	
	public ArticleVendu() {}
	
	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres, int miseAPrix, int prixVente, String etatVente, int meilleurOffre, String pseudoMeilleurAcheteur,boolean archivage, Utilisateur vendeur,
			Retrait retrait, Categorie categorie, String imageUrl) {
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.meilleureOffre = meilleurOffre;
		this.pseudoMeilleurAcheteur = pseudoMeilleurAcheteur;
		this.archivage = archivage;
		this.vendeur = vendeur;
		this.retrait = retrait;
		this.categorie = categorie;
		this.imageUrl = imageUrl;
	}

	public int getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(LocalDateTime dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public LocalDateTime getDateFinEncheres() {
		return dateFinEncheres;
	}

	public String getDateFinEncheresToString() {
		return dateFinEncheres.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH:mm", Locale.FRENCH));
	}

	public void setDateFinEncheres(LocalDateTime dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getMiseAPrix() {
		return miseAPrix;
	}

	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public String getEtatVente() {
		return etatVente;
	}

	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}

	public int getMeilleureOffre() {
		return meilleureOffre;
	}

	public void setMeilleureOffre(int meilleureOffre) {
		this.meilleureOffre = meilleureOffre;
	}

	public String getPseudoMeilleurAcheteur() {
		return pseudoMeilleurAcheteur;
	}

	public void setPseudoMeilleurAcheteur(String pseudoMeilleurAcheteur) {
		this.pseudoMeilleurAcheteur = pseudoMeilleurAcheteur;
	}

	public boolean isArchivage() {
		return archivage;
	}

	public void setArchivage(boolean archivage) {
		this.archivage = archivage;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}

	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}

	public Retrait getRetrait() {
		return retrait;
	}

	public void setRetrait(Retrait retrait) {
		this.retrait = retrait;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(noArticle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticleVendu other = (ArticleVendu) obj;
		return noArticle == other.noArticle;
	}

	@Override
	public String toString() {
		return "ArticleVendu [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebutEncheres=" + dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres + ", miseAPrix="
				+ miseAPrix + ", prixVente=" + prixVente + ", etatVente=" + etatVente + ", meilleureOffre="
				+ meilleureOffre + ", pseudoMeilleurAcheteur=" + pseudoMeilleurAcheteur + "archivage=" + archivage + ", vendeur=" + vendeur
				+ ", retrait=" + retrait + ", categorie=" + categorie + ", imageUrl= " + imageUrl + "]";
	}
	
	
	
}
