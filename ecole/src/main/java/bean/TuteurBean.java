package bean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import java.util.List;
import java.util.ArrayList;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import config.EmailUtil;
import config.PdfUtil;
import entitee.Eleve;
import entitee.Personne;
import entitee.PersonneHome;
import entitee.Role;
import entitee.RoleHome;
import entitee.Tuteur;
import entitee.TuteurHome;
import metier.Operation;

@ManagedBean
@RequestScoped
public class TuteurBean {

    private Tuteur tuteur = new Tuteur();
    private Personne personne = new Personne();
    private List<Eleve> eleves = new ArrayList<>(); // Nouvelle propriété pour stocker les élèves

    private PersonneHome personneHome = new PersonneHome();
    private TuteurHome tuteurHome = new TuteurHome();
    private Role role = new Role();
    private Operation operation = new Operation();
    private Eleve selectedEleve;

    // Méthode appelée après la création du bean
    @PostConstruct
    public void init() {
        // Récupérer le tuteur depuis la session
        tuteur = (Tuteur) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tuteur");
        if (tuteur != null) {
            // Initialiser la liste des élèves associés au tuteur
            eleves = new ArrayList<>(tuteur.getEleves());
        }
    }

    // Getter et Setter pour la liste des élèves
    public List<Eleve> getEleves() {
        return eleves;
    }

    public void setEleves(List<Eleve> eleves) {
        this.eleves = eleves;
    }

    // Getter et Setter pour le tuteur
    public Tuteur getTuteur() {
        return tuteur;
    }

    public void setTuteur(Tuteur tuteur) {
        this.tuteur = tuteur;
    }

    // Getter et Setter pour la personne
    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }
    
    public Eleve getSelectedEleve() {
        return selectedEleve;
    }

    public void setSelectedEleve(Eleve selectedEleve) {
        this.selectedEleve = selectedEleve;
    }
    
    public String voirDetailsEleve() {
        if (selectedEleve != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedEleve", selectedEleve);
            return "eleveDetails?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez sélectionner un élève."));
            return null; // Reste sur la même page
        }
    }


    // Méthode pour l'inscription du tuteur
    public String inscrire() {
        try {
            // Hacher le mot de passe avant de le persister
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(personne.getPassword());
            personne.setPassword(hashedPassword);
            
            // Assigner le rôle "TUTEUR" à la personne
            role = operation.getRole("TUTEUR");
            personne.setRole(role);

            // Persister l'entité Personne
            personneHome.persist(personne);

            // Lier le Tuteur avec la Personne
            tuteur.setPersonne(personne);

            // Persister l'entité Tuteur
            tuteurHome.persist(tuteur);

            // Créer un fichier PDF pour l'email
            String filePath = "C:\\Users\\dione\\Documents\\doc_pdf\\mail_tuteur.pdf"; 
            String logoPath = "C:\\Users\\dione\\Documents\\doc_pdf\\ph.jpg";
            PdfUtil.createPdf(filePath, personne, logoPath);
            
            // Envoi de l'email de confirmation d'inscription
            String to = personne.getEmail(); 
            String subject = "Inscription réussie avec pièce jointe";
            String body = "Bonjour " + personne.getPrenom() + ",\n\n"
                        + "Votre inscription en tant que tuteur est confirmée.\n"
                        + "Merci de nous avoir rejoints ! Veuillez trouver ci-joint votre document PDF.";
            EmailUtil.sendEmailWithAttachment(to, subject, body, filePath);

            return "confirmation"; // Page de confirmation après inscription réussie
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Si une erreur se produit, rester sur la même page
        }
    }
    
}
