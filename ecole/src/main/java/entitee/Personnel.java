package entitee;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "personnel", schema = "public")
public class Personnel implements java.io.Serializable {

	private int idPersonnel;
	private Personne personne;
	private String role;
	private Set<BusTransport> busTransports = new HashSet<BusTransport>(0); // Relation avec BusTransport

	public Personnel() {
	}

	public Personnel(int idPersonnel) {
		this.idPersonnel = idPersonnel;
	}

	public Personnel(int idPersonnel, Personne personne, String role, Set<BusTransport> busTransports) {
		this.idPersonnel = idPersonnel;
		this.personne = personne;
		this.role = role;
		this.busTransports = busTransports;
	}

	@Id
	@Column(name = "id_personnel", unique = true, nullable = false)
	@GeneratedValue
	public int getIdPersonnel() {
		return this.idPersonnel;
	}

	public void setIdPersonnel(int idPersonnel) {
		this.idPersonnel = idPersonnel;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_personne")
	public Personne getPersonne() {
		return this.personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	@Column(name = "role", length = 254)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "personnel")
	public Set<BusTransport> getBusTransports() {
		return this.busTransports;
	}

	public void setBusTransports(Set<BusTransport> busTransports) {
		this.busTransports = busTransports;
	}

}
