package bean;



import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import config.EmailUtil;
import config.PdfUtil;
import entitee.Cours;
import entitee.CoursHome;
import entitee.Personne;
import entitee.PersonneHome;
import entitee.Professeur;
import entitee.ProfesseurHome;
import entitee.Role;
import metier.Operation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@RequestScoped
public class ProfesseurBean {

    Professeur professeur = new Professeur();
    Personne personne = new Personne();
    
	private int selectedCoursId;
    private List<Cours> coursList=new ArrayList<Cours>();
	Cours cours=new Cours();
	Operation op=new Operation();
	@PostConstruct
    private void init() {
        try {
            coursList = op.listeCours();
            

            // Debug
            System.out.println("Cours loaded: " + coursList.size());
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public int getSelectedCoursId() {
		return selectedCoursId;
	}

	public void setSelectedCoursId(int selectedCoursId) {
		this.selectedCoursId = selectedCoursId;
	}

	public List<Cours> getCoursList() {
		return coursList;
	}

	public void setCoursList(List<Cours> coursList) {
		this.coursList = coursList;
	}

	public Cours getCours() {
		return cours;
	}

	public void setCours(Cours cours) {
		this.cours = cours;
	}

	public Professeur getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    PersonneHome personneHome = new PersonneHome();
    ProfesseurHome professeurHome = new ProfesseurHome();
    CoursHome coursHome=new CoursHome();
    Role role=new Role();

    public String inscrire() {
        try {
            // Hacher le mot de passe avant de le persister
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(personne.getPassword());
            personne.setPassword(hashedPassword);

            // Récupérer le cours sélectionné
            role = op.getRole("PROFESSEUR");
            cours = coursHome.findById(selectedCoursId);
            professeur.setCours(cours);
            professeur.setPersonne(personne);
            personne.setRole(role);

            // Persister la personne
            personneHome.persist(personne);
            // Persister le professeur
            professeurHome.persist(professeur);

            // Créer un PDF en mémoire
            String filePath = "D:\\Projets\\JEE\\mail_professeur.pdf"; // Chemin du fichier PDF à créer
            String logoPath = "D:\\Projets\\JEE\\kame.jpg";
            PdfUtil.createPdf(filePath, personne, logoPath);

            // Envoi de l'email de confirmation d'inscription avec le PDF en pièce jointe
            String to = personne.getEmail(); // Email du destinataire
            String subject = "Inscription réussie avec pièce jointe";
            String body = "Bonjour " + personne.getPrenom() + ",\n\n"
                        + "Votre inscription en tant que professeur est confirmée.\n"
                        + "Merci de nous avoir rejoints ! Veuillez trouver ci-joint votre document PDF.";

            // Envoi de l'email avec la pièce jointe PDF
            EmailUtil.sendEmailWithAttachment(to, subject, body, filePath);
            System.out.println(body);

            // Rediriger vers la page de connexion après l'inscription réussie
            return "connexion.xhtml?faces-redirect=true";

        } catch (Exception e) {
            e.printStackTrace();
            return null; // En cas d'erreur, rester sur la même page
        }
    }

}
