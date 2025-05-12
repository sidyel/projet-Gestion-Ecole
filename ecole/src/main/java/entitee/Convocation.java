package entitee;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "convocation", schema = "public")
public class Convocation {
    @Id
    @GeneratedValue
    private int id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Temporal(TemporalType.TIME)
    private Date heured;

    @Temporal(TemporalType.TIME)
    private Date heuref;

    private String lieu;
    private String motif;
    private String description;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_eleve", nullable = false) // FK vers la table Eleve
    private Eleve eleve; // Relation vers l'élève
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personnel") // FK vers la table Eleve
    private Personnel personnel; // Relation vers l'élève
    
   

    // Getters et Setters
    // ...
    
    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getHeured() {
		return heured;
	}

	public void setHeured(Date heured) {
		this.heured = heured;
	}

	public Date getHeuref() {
		return heuref;
	}

	public void setHeuref(Date heuref) {
		this.heuref = heuref;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
