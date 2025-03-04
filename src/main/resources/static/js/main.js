/*
    Fonctions diverses
 */

function configureNavbarOpacity(){
    document.addEventListener("scroll", function(evt){
        const navbar = document.querySelector(".navbar");
        if(!navbar.classList.contains("navbar-shrink") && window.scrollY > 0){
            navbar.classList.add("navbar-shrink");
        } else if(navbar.classList.contains("navbar-shrink") && window.scrollY === 0){
            navbar.classList.remove("navbar-shrink");
        }
    })
}

// Fonction exécutée lors du chargement de la page

function loadScripts(){
    configureNavbarOpacity();
}

loadScripts();