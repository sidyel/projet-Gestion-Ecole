package bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Hibernate;

import entitee.AssoClasseCours;
import entitee.AssoEleveEvaluation;
import entitee.AssoEleveEvaluationHome;
import entitee.Classes;
import entitee.ClassesHome;
import entitee.Cours;
import entitee.CoursHome;
import entitee.Eleve;
import entitee.Evaluation;
import entitee.EvaluationHome;
import entitee.Professeur;
import entitee.ProfesseurHome;
import metier.Operation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;




@ManagedBean
@SessionScoped
public class EvaluationBean {

   
	Evaluation evaluation=new Evaluation();
	private int profId;  // Ajout de l'ID du professeur
	int selectedCoursId;
	private int selectedClasseId; // ID de la classe sélectionnée
	private List<Classes> classesList=new ArrayList<Classes>();
    private List<Cours> coursList=new ArrayList<Cours>();
    List<Evaluation> evaluations=new ArrayList<Evaluation>();
    Operation op=new Operation();
	Cours cours=new Cours();
	String nomClasse;
	Classes classes=new Classes();
	EvaluationHome evaluationHome=new EvaluationHome();
	ProfesseurHome professeurHome= new ProfesseurHome();
	private int ideval;
	List<AssoEleveEvaluation> assoEleveEvaluations= new ArrayList<AssoEleveEvaluation>();
	

	public List<AssoEleveEvaluation> getAssoEleveEvaluations() {
		return assoEleveEvaluations;
	}

	public void setAssoEleveEvaluations(List<AssoEleveEvaluation> assoEleveEvaluations) {
		this.assoEleveEvaluations = assoEleveEvaluations;
	}

	@PostConstruct
	private void init() {
	    try {
	        FacesContext facesContext = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

	        // Vérifier si l'attribut 'ProfId' est présent dans la session
	        Object profIdSession = session.getAttribute("ProfId");
	        if (profIdSession != null) {
	            this.profId = (int) profIdSession;
	        } else {
	            this.profId = -1;
	            System.out.println("Aucun ProfId trouvé dans la session.");
	        }

	        // Vérifier si l'utilisateur est un élève
	        String role = (String) session.getAttribute("role");
	        if ("ELEVE".equals(role)) {
	            // Récupérer la classe de l'élève depuis la session
	            List<Classes> classesEleve = (List<Classes>) session.getAttribute("classesEleve");
	            if (classesEleve != null && !classesEleve.isEmpty()) {
	                this.nomClasse = classesEleve.get(0).getNom();
	                this.evaluations = op.listeEvaluation(this.nomClasse);
	            }
	        }

	        // Récupérer la matière spécifique depuis la session
	        String matiereEnseignee = (String) session.getAttribute("matiere");

	        if (matiereEnseignee != null) {
	            // Filtrer les cours selon la matière en session
	            coursList = op.listeCours().stream()
	                    .filter(cours -> cours.getMatiere().equals(matiereEnseignee))
	                    .collect(Collectors.toList());
	            System.out.println("Matière trouvée en session : " + matiereEnseignee);
	        } else {
	            coursList = op.listeCours();
	            System.out.println("Aucune matière spécifique en session, chargement de tous les cours.");
	        }

	        // Charger uniquement les classes associées au professeur connecté
	        List<AssoClasseCours> classesProf = (List<AssoClasseCours>) session.getAttribute("classesProf");
	        if (classesProf != null) {
	            classesList = classesProf.stream()
	                    .map(AssoClasseCours::getClasse)
	                    .collect(Collectors.toList());
	            System.out.println("Classes du professeur chargées : " + classesList.size());
	        } else {
	            System.out.println("Aucune classe associée trouvée pour ce professeur.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}
	public int getSelectedCoursId() {
		return selectedCoursId;
	}
	public void setSelectedCoursId(int selectedCoursId) {
		this.selectedCoursId = selectedCoursId;
	}
	CoursHome coursHome=new CoursHome();

	public List<Cours> getCoursList() {
		return coursList;
	}
	public void setCoursList(List<Cours> coursList) {
		this.coursList = coursList;
	}
	ClassesHome classesHome=new ClassesHome();
	AssoEleveEvaluationHome assoEleveEvaluationHome=new AssoEleveEvaluationHome();
	
	
	public String saveEvaluation() {
	    try {
	        cours = coursHome.findById(selectedCoursId);
	        cours.getMatiere();
	        classes = classesHome.findById(selectedClasseId);
	        if (cours == null || classes == null) {
	            // Gérer l'erreur si le cours ou la classe n'existe pas
	            return "erreur";
	        }
	        evaluation.setClasse(classes);
	        evaluation.setCours(cours);
	        Professeur professeur = professeurHome.findById(profId);
	        evaluation.setProfesseur(professeur);
	        // Persister l'évaluation
	        evaluationHome.persist(evaluation);
	        
	        // Récupérer la liste des élèves de la classe
	        List<Eleve> eleves = op.listeEleveClasse(classes.getNom()); // Remplacez par la méthode correcte
	        
	        // Créer une association pour chaque élève
	        for (Eleve eleve : eleves) {
	            AssoEleveEvaluation assoEleveEvaluation = new AssoEleveEvaluation();
	            assoEleveEvaluation.setEvaluation(evaluation); // Associer l'évaluation
	            assoEleveEvaluation.setEleve(eleve); // Associer l'élève
	            assoEleveEvaluation.setNote(0.0f);; // Note par défaut
	            assoEleveEvaluationHome.persist(assoEleveEvaluation); // Persister l'association
	        }
	        
	        return "confirmation"; // Redirige vers une page de confirmation
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "erreur"; // Redirige vers une page d'erreur en cas de problème
	    }
	}

	public String listeEvaluation() {
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
	    String role = (String) session.getAttribute("role");

	    if (!"ELEVE".equals(role)) {
	        // Charger les évaluations de la classe recherchée
	        evaluations = op.listeEvaluation(nomClasse);
	    }
	    return "listeEvaluation?faces-redirect=true";
	}
	
	public String listeEvaluation(String nomClasse) {
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
	    String role = (String) session.getAttribute("role");

	    if (!"ELEVE".equals(role)) {
	        // Charger les évaluations de la classe sélectionnée
	        evaluations = op.listeEvaluation(nomClasse);
	    }
	    return "listeEvaluation?faces-redirect=true";
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
	public List<Evaluation> getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	public String getNomClasse() {
		return nomClasse;
	}
	public void setNomClasse(String nomClasse) {
		this.nomClasse = nomClasse;
	}
	
	 public int getProfId() {
	        return profId;
	    }

	    public void setProfId(int profId) {
	        this.profId = profId;
	    }

		public int getIdeval() {
			return ideval;
		}

		public void setIdeval(int ideval) {
			this.ideval = ideval;
		}
	    
		public void listerAssEleEval() {
		    assoEleveEvaluations = op.listeEleveEvaluation(ideval);

		    if (assoEleveEvaluations == null || assoEleveEvaluations.isEmpty()) {
		        System.out.println("Aucune association élève-évaluation trouvée pour ideval : " + ideval);
		    } else {
		        System.out.println("Nombre d'associations trouvées pour ideval " + ideval + ": " + assoEleveEvaluations.size());
		    }
		}



	
   
}
