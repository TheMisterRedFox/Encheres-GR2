DROP VIEW IF EXISTS vue_details_ventes;

CREATE VIEW vue_details_ventes AS
WITH MeilleureEnchere AS (
    SELECT E.no_article, E.no_utilisateur, E.montant_enchere
    FROM ENCHERES E
    WHERE E.montant_enchere = (
        SELECT MAX(E2.montant_enchere)
        FROM ENCHERES E2
        WHERE E2.no_article = E.no_article
    )
)
SELECT
    AV.no_article, AV.nom_article, AV.description, AV.date_debut_encheres, AV.date_fin_encheres, AV.prix_initial, AV.prix_vente, AV.archivage, AV.image,
    U.no_utilisateur, U.pseudo, U.nom, U.prenom, U.email, U.telephone, U.rue, U.code_postal, U.ville, U.mot_de_passe, U.credit, U.administrateur,
    C.no_categorie, C.libelle,
    R.rue AS retrait_rue, R.code_postal AS retrait_code_postal, R.ville AS retrait_ville,
    COALESCE(ME.montant_enchere, AV.prix_initial) AS meilleure_offre, U2.pseudo AS acheteur_pseudo
FROM ARTICLES_VENDUS AV
         INNER JOIN UTILISATEURS U ON U.no_utilisateur = AV.no_utilisateur
         INNER JOIN CATEGORIES C ON C.no_categorie = AV.no_categorie
         INNER JOIN RETRAITS R ON R.no_article = AV.no_article
         LEFT JOIN MeilleureEnchere ME ON ME.no_article = AV.no_article
         LEFT JOIN UTILISATEURS U2 ON U2.no_utilisateur = ME.no_utilisateur;