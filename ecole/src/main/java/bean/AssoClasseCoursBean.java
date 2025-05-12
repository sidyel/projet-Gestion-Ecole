package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import entitee.AssoClasseCours;
import entitee.AssoClasseCoursHome;
import entitee.AssoClasseCoursId;
import entitee.Classes;
import entitee.ClassesHome;
import entitee.Cours;
import entitee.CoursHome;
import entitee.Professeur;
import entitee.ProfesseurHome;
import entitee.Salle;
import entitee.SalleHome;
import metier.Operation;

@ManagedBean
public class AssoClasseCoursBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int selectedClasseId; // ID de la classe sélectionnée
	private int selectedCoursId;
	private int selectedSalleId;
	private int selectedProfId;
	public int getSelectedProfId() 
	{
		return selectedProfId;
	}
	public void setSelectedProfId(int selectedProfId) {
		this.selectedProfId = selectedProfId;
	}
	public int getSelectedSalleId() {
		return selectedSalleId;
	}
	public void setSelectedSalleId(int selectedSalleId) {
		this.selectedSalleId = selectedSalleId;
	}
	public int getSelectedClasseId() {
		return selectedClasseId;
	}
	public void setSelectedClasseId(int selectedClasseId) {
		this.selectedClasseId = selectedClasseId;
	}
	public int getSelectedCoursId() {
		return selectedCoursId;
	}
	public void setSelectedCoursId(int selectedCoursId) {
		this.selectedCoursId = selectedCoursId;
	}


	Operation op = new Operation();

	private List<Classes> classesList=new ArrayList<Classes>();
    private List<Cours> coursList=new ArrayList<Cours>();
    private List<Salle> salleList=new ArrayList<Salle>();
    private List<Professeur> profList=new ArrayList<Professeur>();

    @PostConstruct
    private void init() {
        try {
            // Récupération du rôle depuis la session
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            HttpSession session = (HttpSession) externalContext.getSession(false);
            String role = (String) session.getAttribute("role");

            // Filtrage des classes selon le rôle
            if ("directeur".equalsIgnoreCase(role)) {
                classesList = op.listeClasseByCycle("elementaire");
            } else if ("PRINCIPAL".equalsIgnoreCase(role)) {
                classesList = op.listeClasseByCycle("moyen");
            }
            else if ("PROVISEUR".equalsIgnoreCase(role)) {
                classesList = op.listeClasseByCycle("lycee");
            }
            else {
                classesList = op.listeClasse(); // Charge toutes les classes pour les autres rôles
            }

            // Autres initialisations
            coursList = op.listeCours();
            salleList = op.listeSalle();
            profList = op.listeProfesseur();

            // Debug pour vérifier les classes chargées
            System.out.println("Classes loaded for role " + role + ": " + classesList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	Classes classes=new Classes();
	Cours cours=new Cours();
	Salle salle=new Salle();
	Professeur prof=new Professeur();

	public Salle getSalle() {
		return salle;
	}
	public void setSalle(Salle salle) {
		this.salle = salle;
	}
	public Cours getCours() {
		return cours;
	}
	public void setCours(Cours cours) {
		this.cours = cours;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	

	public List<Professeur> getProfList() {
		return profList;
	}
	public void setProfList(List<Professeur> profList) {
		this.profList = profList;
	}
	public Professeur getProf() {
		return prof;
	}
	public void setProf(Professeur prof) {
		this.prof = prof;
	}


	AssoClasseCours classeCours=new AssoClasseCours();
	
	AssoClasseCoursHome acch=new AssoClasseCoursHome();
	
	public AssoClasseCours getClasseCours() {
		return classeCours;
	}
	public void setClasseCours(AssoClasseCours classeCours) {
		this.classeCours = classeCours;
	}
	public List<Classes> getClassesList() {
		return classesList;
	}
	public void setClassesList(List<Classes> classesList) {
		this.classesList = classesList;
	}
	public List<Cours> getCoursList() {
		return coursList;
	}
	public List<Salle> getSalleList() {
		return salleList;
	}
	public void setSalleList(List<Salle> salleList) {
		this.salleList = salleList;
	}
	public void setCoursList(List<Cours> coursList) {
		this.coursList = coursList;
	}
	
	ClassesHome classesHome=new ClassesHome();
	CoursHome ch=new CoursHome();
	SalleHome sallehome=new SalleHome();
	ProfesseurHome profHome=new ProfesseurHome();
	public String ajouterClasseCours()
	{
		
		classes=classesHome.findById(selectedClasseId);
		cours=ch.findById(selectedCoursId);
		salle=sallehome.findById(selectedSalleId);
		prof=profHome.findById(selectedProfId);
		//op.enregistrerClasseEtProf(classes, prof);
		op.enregistrerSalleEtCours(salle, cours);
		classeCours.setProfesseur(prof);
		classeCours.setClasses(classes);
		classeCours.setCours(cours);
		acch.persist(classeCours);
		 
		System.out.println("ggggggggggggggg");
		return null;
	}
	

}
