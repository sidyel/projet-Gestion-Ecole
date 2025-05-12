package bean;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import entitee.AssoClasseCours;
import entitee.Classes;
import entitee.Eleve;
import entitee.Personne;
import entitee.Personnel;
import entitee.Professeur;
import entitee.Tuteur;
import metier.Operation;

@ManagedBean
public class PersonneBean {
	
	FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    HttpSession session = (HttpSession) externalContext.getSession(true);
	
	Personne personne=new Personne();
	
	Operation operation=new Operation();

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}
	
	public String connexion() {
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    Personne foundPersonne = operation.connexion(personne.getLogin());

	    if (foundPersonne == null) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Login incorrect.");
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        return null;
	    }

	    // Vérification du mot de passe
	    if (!passwordEncoder.matches(personne.getPassword(), foundPersonne.getPassword())) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Mot de passe incorrect.");
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        return null;
	    }

	    try {
	        // Si la connexion est réussie
	        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

	        switch (foundPersonne.getRole().getNomRole()) {
	            case "ELEVE":
	                System.out.println("Connexion réussie en tant qu'élève");
	                Eleve eleve = operation.getEleve(foundPersonne.getIdPersonne());
	                Double moyenneGenerale = operation.calculerMoyenneGeneraleEleve(eleve);
	                if (moyenneGenerale == null) {
	                    moyenneGenerale = 0.0; // Valeur par défaut si la moyenne est nulle
	                }
	                session.setAttribute("moyenneGenerale", moyenneGenerale);

	                int nombreAbsences = eleve.getNbAbsences();
	                session.setAttribute("eleve", eleve);
	                session.setAttribute("role", "ELEVE");
	                session.setAttribute("login", foundPersonne.getLogin());
	                session.setAttribute("nomEleve", eleve.getPersonne().getNom()); // Ajout du nom dans la session
	                session.setAttribute("moyenneGenerale", moyenneGenerale);
	                session.setAttribute("nombreAbsences", nombreAbsences); // Stocker dans la session

	                // Récupération et affichage des classes de l'élève
	                List<Classes> classesEleve = operation.getClassesParEleve(eleve.getIdEleve());
	                session.setAttribute("classesEleve", classesEleve);
	                classesEleve.forEach(classe -> System.out.println("Classe de l'élève : " + classe.getNom()));

	                operation.verifierUtilisateurConnecte(); // Appel de la vérification
	                FacesContext.getCurrentInstance().getExternalContext().redirect("eleve_home.xhtml");
	                break;

	            case "PROFESSEUR":
	            	// Connexion réussie en tant que professeur
	            	System.out.println("Connexion réussie en tant que professeur");
	            	Professeur professeur = operation.getProfesseur(foundPersonne.getIdPersonne());

	            	// Récupération de l'ID du professeur
	            	int idProf = professeur.getIdProfesseur();

	            	// Enregistrement des attributs dans la session
	            	session.setAttribute("prof", professeur);
	            	session.setAttribute("role", "PROFESSEUR");
	            	session.setAttribute("login", foundPersonne.getLogin());
	            	session.setAttribute("nomProf", professeur.getPersonne().getNom());
	            	session.setAttribute("ProfId", idProf); // Ajout de l'ID du professeur

	            	// Appel de getMatieresParProfesseur
	            	List<String> matieres = operation.getMatieresParProfesseur(professeur.getIdProfesseur());
	            	if (!matieres.isEmpty()) {
	            	    session.setAttribute("matiere", matieres.get(0));
	            	    matieres.forEach(matiere -> System.out.println("Matière enseignée : " + matiere));
	            	} else {
	            	    System.out.println("Aucune matière enseignée.");
	            	}

	            	// Appel à la méthode pour récupérer les classes du professeur
	            	List<AssoClasseCours> classesProf = operation.lesClassesDuProfesseur(idProf);
	            	if (classesProf != null && !classesProf.isEmpty()) {
	            	    session.setAttribute("classesProf", classesProf); // Ajout des classes à la session
	            	    classesProf.forEach(c -> System.out.println("Classe associée : " + c));
	            	} else {
	            	    System.out.println("Le professeur ne dispense aucune classe.");
	            	}

	            	// Vérification de l'utilisateur connecté
	            	operation.verifierUtilisateurConnecte();

	            	// Redirection vers la page d'accueil du professeur
	            	FacesContext.getCurrentInstance().getExternalContext().redirect("professeur_home.xhtml");

	                break;


	                // Récupération du tuteur et de ses élèves
	            case "TUTEUR":
	                System.out.println("Connexion réussie en tant que tuteur");

	                // Récupération du tuteur et de ses élèves
	                Tuteur tuteur = operation.getTuteur(foundPersonne.getIdPersonne()); // Assurez-vous que cette méthode utilise JOIN FETCH pour charger les élèves
	                if (tuteur != null) { // Vérifiez si le tuteur est trouvé
	                    session.setAttribute("tuteur", tuteur);
	                    session.setAttribute("role", "TUTEUR");
	                    session.setAttribute("login", foundPersonne.getLogin());

	                    // Affichez les informations du tuteur dans la console
	                    System.out.println("Nom du tuteur : " + tuteur.getPersonne().getNom());
	                    System.out.println("Email du tuteur : " + tuteur.getPersonne().getEmail());

	                    // Affichez les informations des élèves du tuteur
	                    if (tuteur.getEleves() != null && !tuteur.getEleves().isEmpty()) {
	                        System.out.println("Élèves sous la responsabilité du tuteur :");

	                        // Calcul de la moyenne générale pour chaque élève
	                        for (Eleve eleves : tuteur.getEleves()) {
	                            System.out.println("- Nom de l'élève : " + eleves.getPersonne().getNom());
	                            
	                            // Calcul de la moyenne générale de l'élève
	                            Double moyenneGenerale_ = operation.calculerMoyenneGeneraleEleve(eleves);
	                            if (moyenneGenerale_ == null) {
	                                moyenneGenerale_ = 0.0; // Valeur par défaut si la moyenne est nulle
	                            }

	                            // Afficher la moyenne générale de l'élève
	                            System.out.println("Moyenne générale de " + eleves.getPersonne().getNom() + " : " + moyenneGenerale_);

	                            // Stocker la moyenne générale de l'élève dans la session
	                            session.setAttribute("moyenneGenerale_", moyenneGenerale_);

	                        }
	                    } else {
	                        System.out.println("Aucun élève trouvé pour ce tuteur.");
	                    }

	                    // Redirigez vers la page d'accueil du tuteur
	                    FacesContext.getCurrentInstance().getExternalContext().redirect("tuteur_home.xhtml");
	                } else {
	                    System.out.println("Aucun tuteur trouvé.");
	                    // Gérer le cas où aucun tuteur n'est trouvé
	                }
	                break;





	            case "PRINCIPAL":
	                System.out.println("Connexion réussie en tant que principal");
	                Personnel principal = operation.getPersonnel(foundPersonne.getIdPersonne());
	                session.setAttribute("PRINCIPAL", principal);
	                session.setAttribute("role", "PRINCIPAL");
	                session.setAttribute("login", foundPersonne.getLogin());
	                operation.verifierUtilisateurConnecte(); // Appel de la vérification
	                FacesContext.getCurrentInstance().getExternalContext().redirect("acceuil.xhtml");
	                break;
	                
	            case "DIRECTEUR":
	                System.out.println("Connexion réussie en tant que principal");
	                Personnel directeur = operation.getPersonnel(foundPersonne.getIdPersonne());
	                session.setAttribute("DIRECTEUR", directeur);
	                session.setAttribute("role", "DIRECTEUR");
	                session.setAttribute("login", foundPersonne.getLogin());
	                operation.verifierUtilisateurConnecte(); // Appel de la vérification
	                FacesContext.getCurrentInstance().getExternalContext().redirect("acceuil.xhtml");
	                break;
	                
	            case "PROVISEUR":
	                System.out.println("Connexion réussie en tant que principal");
	                Personnel proviseur = operation.getPersonnel(foundPersonne.getIdPersonne());
	                session.setAttribute("PROVISEUR", proviseur);
	                session.setAttribute("role", "PROVISEUR");
	                session.setAttribute("login", foundPersonne.getLogin());
	                operation.verifierUtilisateurConnecte(); // Appel de la vérification
	                FacesContext.getCurrentInstance().getExternalContext().redirect("acceuil.xhtml");
	                break;
	            case "INFIRMIER":
	                System.out.println("Connexion réussie en tant qu'infirmier");
	                Personnel infirmier = operation.getPersonnel(foundPersonne.getIdPersonne());
	                session.setAttribute("INFIRMIER", infirmier);
	                session.setAttribute("role", "INFIRMIER");
	                session.setAttribute("login", foundPersonne.getLogin());
	                operation.verifierUtilisateurConnecte(); // Appel de la vérification
	                FacesContext.getCurrentInstance().getExternalContext().redirect("portail_infirmerie.xhtml");
	                break;
	            case "COMPTABLE":
	                System.out.println("Connexion réussie en tant que comptable");
	                Personnel comptable = operation.getPersonnel(foundPersonne.getIdPersonne());
	                session.setAttribute("COMPTABLE", comptable);
	                session.setAttribute("role", "COMPTABLE");
	                session.setAttribute("login", foundPersonne.getLogin());
	                operation.verifierUtilisateurConnecte(); // Appel de la vérification
	                FacesContext.getCurrentInstance().getExternalContext().redirect("portail_comptable.xhtml");
	                break;    
	            case "ADMIN":
	                System.out.println("Connexion réussie en tant que administrateur");
	                Personnel admin = operation.getPersonnel(foundPersonne.getIdPersonne());
	                session.setAttribute("ADMIN", admin);
	                session.setAttribute("role", "ADMIN");
	                session.setAttribute("login", foundPersonne.getLogin());
	                operation.verifierUtilisateurConnecte(); // Appel de la vérification
	                FacesContext.getCurrentInstance().getExternalContext().redirect("adminPortal.xhtml");
	                break;    


	            default:
	                System.out.println("Échec de la connexion : rôle inconnu");
	                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Rôle inconnu.");
	                FacesContext.getCurrentInstance().addMessage(null, message);
	                break;
	        }
	        return null;

	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}







}
