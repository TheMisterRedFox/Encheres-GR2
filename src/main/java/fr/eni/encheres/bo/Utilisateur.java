package fr.eni.encheres.bo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class Utilisateur {

	private int noUtilisateur;

	@NotBlank
	@Size(min = 3, max = 30, message = "Le pseudo doit comporter entre 3 et 30 caractères")
	private String pseudo;

	@NotBlank
	@Size(max = 30, message = "Le nom ne doit pas dépasser 30 caractères")
	private String nom;

	@NotBlank
	@Size(max = 30, message = "Le prénom ne doit pas dépasser 30 caractères")
	private String prenom;

	@NotBlank
	@Email
	@Size(min = 3, max = 50, message = "L'email doit comporter entre 3 et 50 caractères")
	private String email;

	@Size(max = 15, message = "Le téléphone ne doit pas dépasser 15 caractères")
	private String telephone;

	@NotBlank
	@Size(max = 250, message = "La rue ne doit pas dépasser 250 caractères")
	private String rue;

	@NotBlank
	@Size(max = 10, message = "Le code postal ne doit pas dépasser 10 caractères")
	private String codePostal;

	@NotBlank
	@Size(max = 250, message = "La ville ne doit pas dépasser 250 caractères")
	private String ville;

	@NotBlank
	@Size(min = 5,max = 100, message = "Le mot de passe doit comporter entre 5 et 100 caractères")
	private String motDePasse;
	private int credit;
	private boolean administrateur;
	
	public Utilisateur() {}

	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse, int credit, boolean administrateur) {
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;
	}

	public int getNoUtilisateur() {
		return noUtilisateur;
	}
	
	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean isAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}

	@Override
	public int hashCode() {
		return Objects.hash(noUtilisateur);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilisateur other = (Utilisateur) obj;
		return noUtilisateur == other.noUtilisateur;
	}

	@Override
	public String toString() {
		return "Utilisateur [noUtilisateur=" + noUtilisateur + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom="
				+ prenom + ", email=" + email + ", telephone=" + telephone + ", rue=" + rue + ", codePostal="
				+ codePostal + ", ville=" + ville + ", motDePasse=" + motDePasse + ", credit=" + credit
				+ ", administrateur=" + administrateur + "]";
	}
		
	
	
}
