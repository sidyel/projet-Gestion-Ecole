package bean;



import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import config.EmailUtil;
import config.PdfUtil;
import entitee.Personne;
import entitee.PersonneHome;
import entitee.Personnel;
import entitee.PersonnelHome;
import entitee.Role;
import metier.Operation;

@ManagedBean
@RequestScoped
public class PersonnelBean {
	
	private String selectedRole; // Pour stocker le rôle sélectionné
    private List<Role> availableRoles; // Liste des rôles disponibles

	
	FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    HttpSession session = (HttpSession) externalContext.getSession(true);

    Personnel personnel = new Personnel();
    Personne personne = new Personne();

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    PersonneHome personneHome = new PersonneHome();
    PersonnelHome personnelHome = new PersonnelHome();
    Operation operation=new Operation();
    Role role=new Role();
    
 // Getter et Setter pour selectedRole et availableRoles
    public String getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }

    public List<Role> getAvailableRoles() {
        return availableRoles;
    }

    public void setAvailableRoles(List<Role> availableRoles) {
        this.availableRoles = availableRoles;
    }
    
 // Méthode pour initialiser la liste des rôles
    @PostConstruct
    public void init() {
        availableRoles = operation.getAllRoles(); // Récupère tous les rôles disponibles
    }

    public String inscrire() {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(personne.getPassword());
            personne.setPassword(hashedPassword);

            // Récupérer le rôle sélectionné par son nom
            role = operation.getRole(selectedRole);
            personne.setRole(role);
            personnel.setRole(role.getNomRole());

            // Persist la personne et le personnel
            personneHome.persist(personne);
            personnel.setPersonne(personne);
            personnelHome.persist(personnel);

            // Génération du PDF et envoi d'email (inchangé)
            String filePath = "D:\\Projets\\JEE\\mail_personnel.pdf";
            String logoPath = "D:\\Projets\\JEE\\kame.jpg";
            PdfUtil.createPdf(filePath, personne, logoPath);
            EmailUtil.sendEmailWithAttachment(personne.getEmail(), "Inscription réussie", 
                "Bonjour " + personne.getPrenom() + ", votre inscription est confirmée.", filePath);

            return "connexion?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Erreur lors de l'inscription.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }
    }

    
   
    
}
