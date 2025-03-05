DROP FUNCTION ajouter_vente
CREATE OR REPLACE FUNCTION ajouter_vente(
    p_nom_article VARCHAR,
    p_description VARCHAR,
    p_date_debut_encheres TIMESTAMP,
    p_date_fin_encheres TIMESTAMP,
    p_prix_initial INTEGER,
    p_no_utilisateur INTEGER,
    p_no_categorie INTEGER,
    p_image VARCHAR,
    p_rue VARCHAR,
    p_code_postal VARCHAR,
    p_ville VARCHAR
) RETURNS INTEGER AS $$
DECLARE
v_no_article INTEGER;
BEGIN
    -- Insertion dans la table articles_vendus
INSERT INTO articles_vendus (
    nom_article, description, date_debut_encheres, date_fin_encheres,
    prix_initial, no_utilisateur, no_categorie, image
) VALUES (
             p_nom_article, p_description, p_date_debut_encheres, p_date_fin_encheres,
             p_prix_initial, p_no_utilisateur, p_no_categorie, p_image
         ) RETURNING no_article INTO v_no_article;

-- Insertion dans la table retraits
INSERT INTO retraits (
    no_article, rue, code_postal, ville
) VALUES (
             v_no_article, p_rue, p_code_postal, p_ville
         );

RETURN v_no_article;
END;
$$ LANGUAGE plpgsql;