-- Script de création de la base de données ENCHERES
-- type : PostgreSQL

DROP TABLE IF EXISTS ENCHERES;
DROP TABLE IF EXISTS RETRAITS;
DROP TABLE IF EXISTS ARTICLES_VENDUS;
DROP TABLE IF EXISTS UTILISATEURS;
DROP TABLE IF EXISTS CATEGORIES;

CREATE TABLE CATEGORIES (
    no_categorie SERIAL PRIMARY KEY,
    libelle VARCHAR(30) NOT NULL
);

CREATE TABLE UTILISATEURS (
    no_utilisateur SERIAL PRIMARY KEY,
    pseudo VARCHAR(30) NOT NULL,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    telephone VARCHAR(15),
    rue VARCHAR(250) NOT NULL,
    code_postal VARCHAR(10) NOT NULL,
    ville VARCHAR(250) NOT NULL,
    mot_de_passe VARCHAR(200) NOT NULL,
    credit INTEGER NOT NULL,
    administrateur BOOLEAN NOT NULL
);

CREATE TABLE ARTICLES_VENDUS (
    no_article SERIAL PRIMARY KEY,
    nom_article VARCHAR(100) NOT NULL,
    description VARCHAR(300) NOT NULL,
    date_debut_encheres TIMESTAMP NOT NULL,
    date_fin_encheres TIMESTAMP NOT NULL,
    prix_initial INTEGER,
    prix_vente INTEGER,
    archivage BOOLEAN DEFAULT FALSE,
    image VARCHAR(200),
    no_utilisateur INTEGER NOT NULL,
    no_categorie INTEGER NOT NULL,
    CONSTRAINT articles_vendus_categories_fk FOREIGN KEY (no_categorie) REFERENCES CATEGORIES (no_categorie) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT ventes_utilisateur_fk FOREIGN KEY (no_utilisateur) REFERENCES UTILISATEURS (no_utilisateur) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE RETRAITS (
    no_article INTEGER PRIMARY KEY,
    rue VARCHAR(250) NOT NULL,
    code_postal VARCHAR(15) NOT NULL,
    ville VARCHAR(250) NOT NULL,
    CONSTRAINT retrait_article_fk FOREIGN KEY (no_article) REFERENCES ARTICLES_VENDUS (no_article) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE ENCHERES (
    no_enchere SERIAL PRIMARY KEY,
    date_enchere TIMESTAMP NOT NULL,
    montant_enchere INTEGER NOT NULL,
    no_article INTEGER NOT NULL,
    no_utilisateur INTEGER NOT NULL,
    CONSTRAINT encheres_utilisateur_fk FOREIGN KEY (no_utilisateur) REFERENCES UTILISATEURS (no_utilisateur) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT encheres_no_article_fk FOREIGN KEY (no_article) REFERENCES ARTICLES_VENDUS (no_article) ON DELETE NO ACTION ON UPDATE NO ACTION
);
