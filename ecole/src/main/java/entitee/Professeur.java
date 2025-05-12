package entitee;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
// Other imports...
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "professeur", schema = "public")
public class Professeur implements java.io.Serializable {

    private int idProfesseur;
    private Cours cours;
    private Personne personne;
    private Integer note;
    private int salaire;
    private Set<Ecole> ecoles = new HashSet<Ecole>(0);
    private Set<Classes> classes = new HashSet<Classes>(0); // ManyToMany relation
    private Set<Evaluation> evaluations = new HashSet<>(0); // Relation OneToMany avec Evaluation


    // Constructors, getters, and setters...

    @Id
    @Column(name = "id_professeur", unique = true, nullable = false)
    @GeneratedValue
    public int getIdProfesseur() {
        return this.idProfesseur;
    }

    public void setIdProfesseur(int idProfesseur) {
        this.idProfesseur = idProfesseur;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cours")
    public Cours getCours() {
        return this.cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_personne")
    public Personne getPersonne() {
        return this.personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    @Column(name = "note")
    public Integer getNote() {
        return this.note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "asso_prof_classe", schema = "public", 
               joinColumns = { @JoinColumn(name = "id_professeur", nullable = false, updatable = false) }, 
               inverseJoinColumns = { @JoinColumn(name = "idclasse", nullable = false, updatable = false) })
    public Set<Classes> getClasses() {
        return this.classes;
    }

    public void setClasses(Set<Classes> classes) {
        this.classes = classes;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "asso_prof_ecole", schema = "public", 
               joinColumns = { @JoinColumn(name = "id_professeur", nullable = false, updatable = false) }, 
               inverseJoinColumns = { @JoinColumn(name = "id_ecole", nullable = false, updatable = false) })
    public Set<Ecole> getEcoles() {
        return this.ecoles;
    }

    public void setEcoles(Set<Ecole> ecoles) {
        this.ecoles = ecoles;
    }
    
    
    @Column(name = "frais_inscription")
	public int getSalaire() {
		return salaire;
	}

	public void setSalaire(int salaire) {
		this.salaire = salaire;
	}
	
    private Set<AssoClasseCours> assoClasseCours = new HashSet<AssoClasseCours>(0); // Optionnel
    @OneToMany(mappedBy = "professeur", fetch = FetchType.LAZY) // Relation inverse
    public Set<AssoClasseCours> getAssoClasseCours() {
        return this.assoClasseCours;
    }

    public void setAssoClasseCours(Set<AssoClasseCours> assoClasseCours) {
        this.assoClasseCours = assoClasseCours;
    }
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "professeur")  // Relation OneToMany avec Evaluation
    public Set<Evaluation> getEvaluations() {
        return this.evaluations;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }
}
