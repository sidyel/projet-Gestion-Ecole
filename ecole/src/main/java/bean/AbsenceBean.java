package bean;

import javax.faces.bean.ManagedBean;

import entitee.Absence;
import entitee.AbsenceHome;
import entitee.Eleve;
import entitee.EleveHome;

@ManagedBean
public class AbsenceBean {
	
	Absence absence=new Absence();
	int eleveId;
	Eleve eleve=new Eleve();

	public int getEleveId() {
		return eleveId;
	}

	public void setEleveId(int eleveId) {
		this.eleveId = eleveId;
	}

	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}

	public Absence getAbsence() {
		return absence;
	}

	public void setAbsence(Absence absence) {
		this.absence = absence;
	}
	EleveHome eleveHome=new EleveHome();
	AbsenceHome absenceHome=new AbsenceHome();
	
	public String addAbsence()
	{
		eleve=eleveHome.findById(eleveId);
		eleve.getAbsences().add(absence);
		eleve.setNbAbsences(eleve.getNbAbsences()+1);
		eleveHome.merge(eleve);
		absence.setEleve(eleve);
		absenceHome.persist(absence);
		return null;
	}
	

}
