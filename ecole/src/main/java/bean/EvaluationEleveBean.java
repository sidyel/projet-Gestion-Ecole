package bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import entitee.AssoEleveEvaluation;
import entitee.AssoEleveEvaluationHome;
import metier.Operation;

@ManagedBean
public class EvaluationEleveBean {
	
	@ManagedProperty("#{evaluationBean}")
	private EvaluationBean evaluationBean;

	public void setEvaluationBean(EvaluationBean evaluationBean) {
	    this.evaluationBean = evaluationBean;
	}
	
	AssoEleveEvaluation eleveEvaluation=new AssoEleveEvaluation();
	 private Long idEvaluation;
	 
	 

	public AssoEleveEvaluation getEleveEvaluation() {
		return eleveEvaluation;
	}

	public void setEleveEvaluation(AssoEleveEvaluation eleveEvaluation) {
		this.eleveEvaluation = eleveEvaluation;
	}
	
	List<AssoEleveEvaluation> assoEleveEvaluations=new ArrayList<AssoEleveEvaluation>();
	Operation operation=new Operation();
	
	String nomClasse;
	
	
	@PostConstruct
    public void init() {
        nomClasse = "CE2"; // Valeur par défaut
        assoEleveEvaluations = operation.listeEleveEvaluation(nomClasse);
    }

    public void listerAssEleEval() {
        if (nomClasse != null && !nomClasse.trim().isEmpty()) {
            assoEleveEvaluations = operation.listeEleveEvaluation(nomClasse);
        } else {
            System.out.println("Le nom de la classe est vide ou nul.");
        }
    }

	AssoEleveEvaluationHome home=new AssoEleveEvaluationHome();
	
	
	public void sauvegarderNote() {
	    List<AssoEleveEvaluation> assoEleveEvaluations = evaluationBean.getAssoEleveEvaluations();

	    if (assoEleveEvaluations == null || assoEleveEvaluations.isEmpty()) {
	        System.out.println("Aucune évaluation à sauvegarder.");
	        return;
	    }

	    System.out.println("Nombre d'éléments dans assoEleveEvaluations : " + assoEleveEvaluations.size());

	    try {
	        for (AssoEleveEvaluation assoEleveEvaluation : assoEleveEvaluations) {
	            if (assoEleveEvaluation.getNote() == null || assoEleveEvaluation.getNote() < 0 || assoEleveEvaluation.getNote() > 20) {
	                System.out.println("Note invalide pour l'élève : " + assoEleveEvaluation.getEleve().getPersonne().getNom());
	                continue;
	            }
	            
	            home.merge(assoEleveEvaluation);
	            System.out.println("Évaluation sauvegardée pour l'élève : " + assoEleveEvaluation.getEleve().getPersonne().getNom());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}




	public List<AssoEleveEvaluation> getAssoEleveEvaluations() {
		return assoEleveEvaluations;
	}

	public void setAssoEleveEvaluations(List<AssoEleveEvaluation> assoEleveEvaluations) {
		this.assoEleveEvaluations = assoEleveEvaluations;
	}

	public String getNomClasse() {
		return nomClasse;
	}

	public void setNomClasse(String nomClasse) {
		this.nomClasse = nomClasse;
	}
	
	public void setEvaluationId(Long idEvaluation) {
	    this.idEvaluation = idEvaluation;
	    System.out.println("ID de l'évaluation sélectionnée : " + idEvaluation);

	    // Redirection vers la page 'attribuerNote.xhtml'
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
	    navigationHandler.handleNavigation(facesContext, null, "ajouterNote?faces-redirect=true");
	}
	
	

}
