package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import config.EmailUtil;
import config.PdfUtil;
import entitee.Absence;
import entitee.AbsenceHome;
import entitee.AssoEleveEvaluation;
import entitee.Classes;
import entitee.ClassesHome;
import entitee.Eleve;
import entitee.EleveHome;
import entitee.Personne;
import entitee.PersonneHome;
import entitee.Role;
import entitee.Tuteur;
import entitee.TuteurHome;
import metier.Operation;

@ManagedBean
@RequestScoped
public class EleveBean {
    
    private int selectedClasseId;
    private String selectedClasse;

    private List<Classes> classesList = new ArrayList<>();
    private List<Eleve> eleveListe;
    private String nom;
    private String prenom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public List<Eleve> getEleveListe() {
        return eleveListe;
    }

    public void setEleveListe(List<Eleve> eleveListe) {
        this.eleveListe = eleveListe;
    }

    Operation op = new Operation();

    @PostConstruct
    private void init() {
        try {
            // Récupération du rôle de l'utilisateur connecté
            String role = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("role");
            
            if ("PRINCIPAL".equals(role)) {
                // Filtrer pour les classes du collège (6e à 3e)
                classesList = op.listeClasseByCycle("moyen");
            } else if ("DIRECTEUR".equals(role)) {
                // Filtrer pour les classes du primaire (CI à CM2)
                classesList = op.listeClasseByCycle("elementaire");
            }
            else if ("PROVISEUR".equals(role)) {
                // Filtrer pour les classes du primaire (CI à CM2)
                classesList = op.listeClasseByCycle("lycee");
            }
            else {
                // Si le rôle est autre, par exemple administrateur, montrer toutes les classes
                classesList = op.listeClasse();
            }

            // Charger les élèves par la première classe comme exemple
            if (!classesList.isEmpty()) {
                eleveListe = op.listeEleveClasse(classesList.get(0).getNom());
            }

            // Debug
            System.out.println("Classes loaded: " + classesList.size());
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Classes classes = new Classes();
    ClassesHome classesHome = new ClassesHome();
 
    Eleve e = new Eleve();
    Personne p = new Personne();
   
    public Eleve getE() {
        return e;
    }

    public void setE(Eleve e) {
        this.e = e;
    }

    public Personne getP() {
        return p;
    }

    public void setP(Personne p) {
        this.p = p;
    }

    PersonneHome ph = new PersonneHome();
    EleveHome eh = new EleveHome();
    Role role = new Role();
    private Personne personne = new Personne(); // Déclaration de la variable
    private Tuteur tuteur = new Tuteur(); // Déclaration de la variable
    private TuteurHome tuteurHome = new TuteurHome(); // Déclaration de la variable
    private Role role1; // Déclaration de la variable


    public String inscrire() {
        try {
        	
        	 // Hacher le mot de passe avant de le persister
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(p.getPassword());
            String hashedPassword1=passwordEncoder.encode(personne.getPassword());
            p.setPassword(hashedPassword);
            personne.setPassword(hashedPassword1);
            // Récupérer la classe sélectionnée
            classes = classesHome.findById(selectedClasseId);
            classes.getEleves().add(e);
            classes.setEffectif(classes.getEffectif() + 1);
            e.setClasses(classes);
            role=op.getRole("ELEVE");
            role1=op.getRole("TUTEUR");
            p.setRole(role);
            personne.setRole(role1);
            

            // Persister la personne et associer l'élève
            ph.persist(personne);
            ph.persist(p);
            tuteur.setPersonne(personne);
            tuteurHome.persist(tuteur);
            e.setTuteur(tuteur);
            e.setPersonne(p);
            e.setNbAbsences(0);
            eh.persist(e);

            // Mettre à jour la classe et les élèves associés
            classes.getEleves().add(e);
            classesHome.merge(classes);

            // Créer un PDF en mémoire
            String filePath = "D:\\Projets\\JEE\\mail_eleve.pdf"; // Chemin du fichier PDF à créer
            String logoPath = "D:\\Projets\\JEE\\kame.jpg";
            PdfUtil.createPdf(filePath, p, logoPath);

            // Envoi de l'email de confirmation d'inscription avec le PDF en pièce jointe
            String to = p.getEmail();  // Email du destinataire
            String subject = "Inscription réussie avec pièce jointe";
            String body = "Bonjour " + p.getPrenom() + ",\n\n"
                        + "Votre inscription à l'école est confirmée.\n"
                        + "Merci de nous avoir rejoints ! Veuillez trouver ci-joint votre document PDF.";

            // Envoi de l'email avec la pièce jointe PDF
            EmailUtil.sendEmailWithAttachment(to, subject, body, filePath);

            return "connexion?faces-redirect=true";  // Redirige vers la page de confirmation d'inscription

        } catch (Exception e) {
            e.printStackTrace();
            return null;  // En cas d'erreur, rester sur la même page
        }
    }
    public void chargerEleve() {
        e = eh.findById(selectedClasseId);
    }
    
    public void rechercherEleve() {
        if (nom != null && !nom.isEmpty() && prenom != null && !prenom.isEmpty()) {
            // Rechercher avec le nom et le prénom
            eleveListe = op.RechercheEleveParNomEtPrenom(nom, prenom);
        } else if (nom != null && !nom.isEmpty()) {
            // Rechercher uniquement par nom
            eleveListe = op.RechercheEleveParNom(nom);
        } else if (prenom != null && !prenom.isEmpty()) {
            // Rechercher uniquement par prénom
            eleveListe = op.RechercheEleveParPrenom(prenom);
        } else {
            // Si aucun champ n'est rempli, retourner tous les élèves ou un message d'erreur
            eleveListe = new ArrayList<>();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Veuillez entrer un nom ou un prénom."));
        }
    }

    
    public void rechercherEleveClasse() {
        eleveListe = op.listeEleveClasse(selectedClasse);
    }
    
    public void saveAppele() {
        System.out.println(eleveListe.size());
        AbsenceHome absenceHome = new AbsenceHome();
        for (Eleve eleve : eleveListe) {
            System.out.println(eleve.isEstPresent());
            if (!eleve.isEstPresent()) {
                Absence absence = new Absence();
                absence.setDate(new Date());
                absence.setEleve(eleve);
                absence.setHeured(new Date());
                absence.setHeuref(new Date());
                absence.setJustificatiion(null);
                
                absenceHome.persist(absence);
            }
        }
    }

    public int getSelectedClasseId() {
        return selectedClasseId;
    }

    public void setSelectedClasseId(int selectedClasseId) {
        this.selectedClasseId = selectedClasseId;
    }

    public List<Classes> getClassesList() {
        return classesList;
    }

    public void setClassesList(List<Classes> classesList) {
        this.classesList = classesList;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public String getSelectedClasse() {
        return selectedClasse;
    }

    public void setSelectedClasse(String selectedClasse) {
        this.selectedClasse = selectedClasse;
    }
    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Tuteur getTuteur() {
        return tuteur;
    }

    public void setTuteur(Tuteur tuteur) {
        this.tuteur = tuteur;
    }
    
} 