-- Insérer des catégories
INSERT INTO CATEGORIES (libelle) VALUES
    ('Électronique'),
    ('Vêtements'),
    ('Mobilier'),
    ('Livres'),
    ('Informatique');

-- Insérer des utilisateurs
INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES
    ('jdoe', 'Doe', 'John', 'jdoe@example.com', '0123456789', '10 rue de Paris', '75001', 'Paris', 'pass123', 100, FALSE),
    ('asmith', 'Smith', 'Alice', 'asmith@example.com', '0987654321', '15 av. de Lyon', '69000', 'Lyon', 'pass123', 200, TRUE),
    ('bmartin', 'Martin', 'Bob', 'bmartin@example.com', '0678901234', '20 bd de Lille', '59000', 'Lille', 'pass123', 50, FALSE);

-- Insérer des articles vendus
INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES
    ('Ordinateur', 'PC 15 pouces', '2025-02-01 10:00:00', '2025-02-07 18:00:00', 500, NULL, 1, 5),
    ('Canapé', 'Canapé cuir', '2025-02-05 12:00:00', '2025-02-10 20:00:00', 300, NULL, 2, 3),
    ('Livre LOTR', 'Édition collector', '2025-02-03 09:00:00', '2025-02-08 17:00:00', 50, NULL, 3, 4);

-- Insérer des retraits
INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES
    (1, '10 rue de Paris', '75001', 'Paris'),
    (2, '15 av. de Lyon', '69000', 'Lyon'),
    (3, '20 bd de Lille', '59000', 'Lille');

-- Insérer des enchères
INSERT INTO ENCHERES (date_enchere, montant_enchere, no_article, no_utilisateur) VALUES
    ('2025-02-02 14:30:00', 520, 1, 2),
    ('2025-02-06 16:00:00', 550, 1, 3),
    ('2025-02-07 17:45:00', 600, 1, 1),
    ('2025-02-05 13:15:00', 320, 2, 1),
    ('2025-02-07 19:00:00', 350, 2, 3),
    ('2025-02-04 10:45:00', 60, 3, 2);
