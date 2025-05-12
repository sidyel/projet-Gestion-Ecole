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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "classes", schema = "public")
public class Classes implements java.io.Serializable {

	private int idclasse;
	private String niveau;
	private String nom;
	private int effectif;
	private int mensualite;
	private int fraisInscription;
	private Set<Eleve> eleves = new HashSet<Eleve>(0);
	private Set<AssoClasseCours> assoClasseCourses = new HashSet<AssoClasseCours>(0);
	private Set<Professeur> professeurs = new HashSet<Professeur>(0); // ManyToMany relation
    private Set<Evaluation> evaluations = new HashSet<Evaluation>(0); // Relation OneToMany avec Evaluation


	// Constructors, getters, and setters...

	@Id
	@Column(name = "idclasse", unique = true, nullable = false)
	@GeneratedValue
	public int getIdclasse() {
		return this.idclasse;
	}

	public void setIdclasse(int idclasse) {
		this.idclasse = idclasse;
	}

	@Column(name = "niveau", length = 254)
	public String getNiveau() {
		return this.niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	@Column(name = "nom", length = 254)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "classes")
	public Set<Eleve> getEleves() {
		return this.eleves;
	}

	public void setEleves(Set<Eleve> eleves) {
		this.eleves = eleves;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "classes")
	public Set<AssoClasseCours> getAssoClasseCourses() {
		return this.assoClasseCourses;
	}

	public void setAssoClasseCourses(Set<AssoClasseCours> assoClasseCourses) {
		this.assoClasseCourses = assoClasseCourses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "asso_prof_classe", schema = "public", joinColumns = {
			@JoinColumn(name = "idclasse", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id_professeur", nullable = false, updatable = false) })
	public Set<Professeur> getProfesseurs() {
		return this.professeurs;
	}

	public void setProfesseurs(Set<Professeur> professeurs) {
		this.professeurs = professeurs;
	}

	@Column(name = "frais_inscription")
	public int getFraisInscription() {
		return fraisInscription;
	}

	public void setFraisInscription(int fraisInscription) {
		this.fraisInscription = fraisInscription;
	}

	@Column(name = "mensualite")
	public int getMensualite() {
		return mensualite;
	}

	public void setMensualite(int mensualite) {
		this.mensualite = mensualite;
	}

	@Column(name = "effectif")
	public int getEffectif() {
		return effectif;
	}

	public void setEffectif(int effectif) {
		this.effectif = effectif;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "classe")
    public Set<Evaluation> getEvaluations() {
        return this.evaluations;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

}
