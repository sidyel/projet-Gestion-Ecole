package entitee;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bus_transport", schema = "public")
public class BusTransport implements java.io.Serializable {

	private int idBus;
	private Ecole ecole;
	private String horaires;
	private String itineraire;
	private Integer prix;
	private Personnel personnel; // Relation avec Personnel
	private Set<Eleve> eleves = new HashSet<Eleve>(0);



	public BusTransport() {
	}

	public BusTransport(int idBus) {
		this.idBus = idBus;
	}

	public BusTransport(int idBus, Ecole ecole, String horaires, String itineraire, Integer prix, Personnel personnel) {
		this.idBus = idBus;
		this.ecole = ecole;
		this.horaires = horaires;
		this.itineraire = itineraire;
		this.prix = prix;
		this.personnel = personnel;
	}

	@Id
	@Column(name = "id_bus", unique = true, nullable = false)
	@GeneratedValue
	public int getIdBus() {
		return this.idBus;
	}

	public void setIdBus(int idBus) {
		this.idBus = idBus;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ecole")
	public Ecole getEcole() {
		return this.ecole;
	}

	public void setEcole(Ecole ecole) {
		this.ecole = ecole;
	}

	@Column(name = "horaires", length = 254)
	public String getHoraires() {
		return this.horaires;
	}

	public void setHoraires(String horaires) {
		this.horaires = horaires;
	}

	@Column(name = "itineraire", length = 254)
	public String getItineraire() {
		return this.itineraire;
	}

	public void setItineraire(String itineraire) {
		this.itineraire = itineraire;
	}

	@Column(name = "prix")
	public Integer getPrix() {
		return this.prix;
	}

	public void setPrix(Integer prix) {
		this.prix = prix;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_personnel")
	public Personnel getPersonnel() {
		return this.personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "busTransport")
	public Set<Eleve> getEleves() {
		return this.eleves;
	}

	public void setEleves(Set<Eleve> eleves) {
		this.eleves = eleves;
	}

}
