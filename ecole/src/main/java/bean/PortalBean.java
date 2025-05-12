package bean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.List;
import entitee.Eleve;
import entitee.Classes;

@ManagedBean(name = "portalBean")
@SessionScoped // ou @RequestScoped selon vos besoins
public class PortalBean {

    public void tracerRole() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String role = (String) session.getAttribute("role"); // Récupère le rôle de l'utilisateur

        if (role != null) {
            System.out.println("Navigation effectuée par : " + role);
        } else {
            System.out.println("Utilisateur sans rôle défini tente de naviguer.");
        }
    }
    
    public String programmerEvaluation() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        // Récupère la matière depuis la session
        String matiere = (String) session.getAttribute("matiere");

        // Afficher le message dans la console
        if (matiere != null) {
            System.out.println("Le prof qui enseigne la matière " + matiere + " veut programmer une évaluation.");
        } else {
            System.out.println("Matière non définie pour le professeur.");
        }

        // Rediriger vers la page progEvaluation.xhtml
        return "progEvaluation.xhtml";
    }
    
    public void tracerNavigationEleve(String pageCible) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        Eleve eleve = (Eleve) session.getAttribute("eleve"); // Récupération de l'élève
        List<Classes> classesEleve = (List<Classes>) session.getAttribute("classesEleve"); // Récupération des classes

        if (eleve != null && classesEleve != null && !classesEleve.isEmpty()) {
            // Accéder au nom via l'objet Personne associé à Eleve
            String nomEleve = eleve.getPersonne().getNom(); // Supposons qu'Eleve ait une référence vers Personne
            String nomClasse = classesEleve.get(0).getNom(); // Supposons qu'il y ait une seule classe
            System.out.println("L'élève " + nomEleve + " de la classe " + nomClasse + " a navigué vers " + pageCible + ".");
        } else {
            System.out.println("Impossible de tracer la navigation : élève ou classe non définie.");
        }
    }

    // Méthodes de navigation
    public String naviguerVersEvaluations() {
        tracerNavigationEleve("Mes évaluations");
        return "listeEvaluationEl.xhtml";
    }

    public String naviguerVersEmploiDuTemps() {
        tracerNavigationEleve("Mon emploi du temps");
        return "afficherEmploiDuTemps.xhtml";
    }
    
    public String logout() {
        // Obtenir la session actuelle
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        
        // Invalider la session si elle existe
        if (session != null) {
            session.invalidate();
        }

        // Rediriger vers la page de connexion ou d'accueil
        return "connexion.xhtml?faces-redirect=true";
    }
    
    // Ajoutez des méthodes pour rediriger vers chaque fonctionnalité si besoin
}
