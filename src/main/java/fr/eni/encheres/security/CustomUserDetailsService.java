package fr.eni.encheres.security;

import fr.eni.encheres.bll.utilisateur.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UtilisateurService userService;

    public CustomUserDetailsService(UtilisateurService userService) {
        super();
        this.userService = userService;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> optUtilisateur = userService.findByPseudo(username);
        if (optUtilisateur.isPresent()) {
            Utilisateur utilisateur = optUtilisateur.get();
            User.UserBuilder userBuilder = User.builder()
                    .username(utilisateur.getPseudo())
                    .password(utilisateur.getMotDePasse())
                    .roles(getRoles(utilisateur));

            return userBuilder.build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private String[] getRoles(Utilisateur user) {
        if (user.isAdministrateur()) {
            return new String[]{"ADMIN"};
        } else {
            return new String[]{"USER"};
        }
    }
}
