package fr.eni.encheres.security;

import fr.eni.encheres.bll.utilisateur.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private UtilisateurService utilisateurService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User userDetails = (User) authentication.getPrincipal();
        Utilisateur utilisateur = ServiceProvider.getUserService().findByPseudo(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        session.setAttribute("user", utilisateur);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private class ServiceProvider {
        private static UtilisateurService userService;

        public static void setUserService(UtilisateurService service) {
            ServiceProvider.userService = service;
        }

        public static UtilisateurService getUserService() {
            return userService;
        }
    }
}


