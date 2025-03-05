package fr.eni.encheres.utilisateur;

import fr.eni.encheres.bll.utilisateur.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exceptions.UsernameAlreadyExistsException;
import fr.eni.encheres.security.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UtilisateurServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.update("DELETE FROM utilisateurs WHERE pseudo = ?", "admintest");

        String encodedPassword = passwordEncoder.encode("Pa$$w0rd");

        jdbcTemplate.update("INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                "admintest", "Test", "Admin", "admin@test.com", "0123456789", "1 rue Test", "75001", "Paris", encodedPassword, 100, true);
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admintest");

        assertNotNull(userDetails);
        assertEquals("admintest", userDetails.getUsername());
        assertTrue(passwordEncoder.matches("Pa$$w0rd", userDetails.getPassword()));
    }

    @Test
    void loadUserByUsername_PasswordShouldBeEncoded() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admintest");
        assertEquals("admintest", userDetails.getUsername());
        assertNotEquals("Pa$$w0rd", userDetails.getPassword());
    }

    @Test
    void createUserWithSameUsername_ShouldThrowException() {
        Utilisateur utilisateurCopieur = new Utilisateur();
        utilisateurCopieur.setPseudo("admintest");

        assertThrows(UsernameAlreadyExistsException.class, () -> utilisateurService.save(utilisateurCopieur));
    }

    @Test
    void loadUserByUsername_ShouldThrowException_WhenUserNotFound() {
        assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("unknownUser"));
    }
}
