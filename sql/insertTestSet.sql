-- Suppression des données existantes
DELETE
FROM ENCHERES;
DELETE
FROM RETRAITS;
DELETE
FROM ARTICLES_VENDUS;
DELETE
FROM UTILISATEURS;
DELETE
FROM CATEGORIES;

-- Réinitialisation des séquences (important pour PostgreSQL)
ALTER SEQUENCE categories_no_categorie_seq RESTART WITH 1;
ALTER SEQUENCE utilisateurs_no_utilisateur_seq RESTART WITH 1;
ALTER SEQUENCE articles_vendus_no_article_seq RESTART WITH 1;
ALTER SEQUENCE encheres_no_enchere_seq RESTART WITH 1;

-- Insertion des catégories avec IDs fixes
INSERT INTO CATEGORIES (no_categorie, libelle)
VALUES (1, 'Électronique'),
       (2, 'Vêtements'),
       (3, 'Mobilier'),
       (4, 'Livres'),
       (5, 'Informatique'),
       (6, 'Jeux vidéo'),
       (7, 'Électroménager'),
       (8, 'Sport'),
       (9, 'Accessoires'),
       (10, 'Voitures');

-- Insertion des utilisateurs
INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit,
                          administrateur)
VALUES ('jdoe', 'Doe', 'John', 'jdoe@example.com', '0123456789', '10 rue de Paris', '75001', 'Paris', '{bcrypt}$2a$10$rtqeU.ZF0V.1Cg.gpELOK.XWG3Bvtl409nfRhrUCFtiEJXR1f0tIe', 100,
        FALSE),
       ('asmith', 'Smith', 'Alice', 'asmith@example.com', '0987654321', '15 av. de Lyon', '69000', 'Lyon', '{bcrypt}$2a$10$TBc7gWbm0vVJaUY5Vn6PxePFoteuAekQFL65FqICsIij.3XjUi3Q.',
        200, TRUE),
       ('bmartin', 'Martin', 'Bob', 'bmartin@example.com', '0678901234', '20 bd de Lille', '59000', 'Lille', '{bcrypt}$2a$10$rtqeU.ZF0V.1Cg.gpELOK.XWG3Bvtl409nfRhrUCFtiEJXR1f0tIe',
        50, FALSE),
       ('cmiller', 'Miller', 'Chris', 'cmiller@example.com', '0612345678', '5 rue de Nantes', '44000', 'Nantes',
        '{bcrypt}$2a$10$/q.yk/7nNGbyIIFK3ZypJebWrQ8/2CFpFeSWdi4XMaCAP6Fb2Kw5m', 150, FALSE),
       ('lgarcia', 'Garcia', 'Laura', 'lgarcia@example.com', '0623456789', '25 place de Toulouse', '31000', 'Toulouse',
        '{bcrypt}$2a$10$VTs45Y0K.2mbaHod1BB7I.fs9CcmH/cA.WXq4GecAAZ.KyQ3oGNG2', 300, FALSE),
       ('ddupont', 'Dupont', 'David', 'ddupont@example.com', '0634567890', '12 rue de Bordeaux', '33000', 'Bordeaux',
        '{bcrypt}$2a$10$/q.yk/7nNGbyIIFK3ZypJebWrQ8/2CFpFeSWdi4XMaCAP6Fb2Kw5m', 250, TRUE),
       ('mleblanc', 'Leblanc', 'Marie', 'mleblanc@example.com', '0645678901', '8 rue de Marseille', '13000',
        'Marseille', '{bcrypt}$2a$10$/q.yk/7nNGbyIIFK3ZypJebWrQ8/2CFpFeSWdi4XMaCAP6Fb2Kw5m', 180, FALSE);

-- Insertion des articles (avec plus de données)
INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente,
                             no_utilisateur, no_categorie)
VALUES ('Ordinateur', 'PC 15 pouces', '2025-02-01 10:00:00', '2025-02-07 18:00:00', 500, NULL, 1, 5),
       ('Canapé', 'Canapé en cuir noir', '2025-02-05 12:00:00', '2025-02-10 20:00:00', 300, NULL, 2, 3),
       ('Livre LOTR', 'Édition collector avec illustrations', '2025-02-03 09:00:00', '2025-02-08 17:00:00', 50, NULL, 3,
        4),
       ('PS5', 'Console Sony PlayStation 5 - Neuve', '2025-02-10 10:00:00', '2025-02-17 18:00:00', 450, NULL, 4, 6),
       ('Montre Rolex', 'Modèle Submariner - Très bon état', '2025-02-12 11:00:00', '2025-02-18 20:00:00', 5000, NULL,
        5, 9),
       ('Chaussures Nike', 'Air Jordan 1 édition limitée', '2025-02-07 10:00:00', '2025-02-12 23:00:00', 120, NULL, 6,
        2),
       ('Machine à café', 'Expresso automatique avec mousseur', '2025-02-06 09:00:00', '2025-02-13 22:00:00', 80, NULL,
        7, 7),
       ('Vélo de course', 'Cadre en carbone ultra-léger', '2025-02-14 08:00:00', '2025-02-20 19:00:00', 1200, NULL, 2,
        8),
       ('Smartphone Samsung', 'Galaxy S23 Ultra - 512Go', '2025-02-15 10:00:00', '2025-02-22 18:00:00', 900, NULL, 3,
        1),
       ('Table en bois massif', 'Chêne naturel - 6 places', '2025-02-09 11:30:00', '2025-02-16 21:00:00', 600, NULL, 4,
        3);

-- Insertion des retraits
INSERT INTO RETRAITS (no_article, rue, code_postal, ville)
VALUES (1, '10 rue de Paris', '75001', 'Paris'),
       (2, '15 av. de Lyon', '69000', 'Lyon'),
       (3, '20 bd de Lille', '59000', 'Lille'),
       (4, '5 rue de Nantes', '44000', 'Nantes'),
       (5, '25 place de Toulouse', '31000', 'Toulouse'),
       (6, '12 rue de Bordeaux', '33000', 'Bordeaux'),
       (7, '8 rue de Marseille', '13000', 'Marseille'),
       (8, '50 av. des Champs-Élysées', '75008', 'Paris'),
       (9, '17 rue de Nice', '06000', 'Nice'),
       (10, '6 impasse des Lilas', '57000', 'Metz');

-- Insertion des enchères
INSERT INTO ENCHERES (date_enchere, montant_enchere, no_article, no_utilisateur)
VALUES ('2025-02-02 14:30:00', 520, 1, 2),
       ('2025-02-04 10:15:00', 540, 1, 3),
       ('2025-02-06 16:00:00', 550, 1, 4),
       ('2025-02-07 17:45:00', 600, 1, 1),

       ('2025-02-05 13:15:00', 320, 2, 1),
       ('2025-02-06 15:00:00', 330, 2, 5),
       ('2025-02-07 19:00:00', 350, 2, 3),
       ('2025-02-08 14:45:00', 370, 2, 6),

       ('2025-02-03 12:00:00', 55, 3, 4),
       ('2025-02-05 18:20:00', 60, 3, 2),
       ('2025-02-07 22:30:00', 70, 3, 7),

       ('2025-02-11 15:00:00', 480, 4, 2),
       ('2025-02-13 16:30:00', 500, 4, 3),
       ('2025-02-14 17:00:00', 520, 4, 5),
       ('2025-02-16 18:00:00', 550, 4, 1),

       ('2025-02-12 11:30:00', 5100, 5, 6),
       ('2025-02-13 14:45:00', 5200, 5, 3),
       ('2025-02-15 18:20:00', 5300, 5, 7),

       ('2025-02-07 10:20:00', 130, 6, 5),
       ('2025-02-08 12:45:00', 140, 6, 2),
       ('2025-02-09 15:30:00', 150, 6, 4),

       ('2025-02-08 10:00:00', 85, 7, 6),
       ('2025-02-09 12:30:00', 95, 7, 1),
       ('2025-02-10 15:15:00', 110, 7, 3),

       ('2025-02-14 09:30:00', 1250, 8, 7),
       ('2025-02-15 14:15:00', 1300, 8, 2),
       ('2025-02-16 16:45:00', 1350, 8, 6),

       ('2025-02-15 11:00:00', 950, 9, 4),
       ('2025-02-16 13:30:00', 980, 9, 5),
       ('2025-02-17 16:00:00', 1000, 9, 1),

       ('2025-02-18 09:00:00', 650, 10, 3),
       ('2025-02-19 12:30:00', 680, 10, 7),
       ('2025-02-20 14:15:00', 700, 10, 2);


SELECT *
FROM CATEGORIES;
SELECT *
FROM UTILISATEURS;
SELECT *
FROM ARTICLES_VENDUS;
SELECT *
FROM RETRAITS;
SELECT *
FROM ENCHERES;