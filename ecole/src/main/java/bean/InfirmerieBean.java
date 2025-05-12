package bean;

import javax.faces.bean.ManagedBean;

import entitee.Eleve;
import entitee.EleveHome;
import entitee.Infirmerie;
import entitee.InfirmerieHome;

@ManagedBean
public class InfirmerieBean {
	
	Infirmerie infirmerie=new Infirmerie();
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

	public Infirmerie getInfirmerie() {
		return infirmerie;
	}

	public void setInfirmerie(Infirmerie infirmerie) {
		this.infirmerie = infirmerie;
	}
	
	InfirmerieHome infirmerieHome=new InfirmerieHome();
	EleveHome eleveHome=new EleveHome();
	public String ajouterConsultation()
	{
		infirmerieHome.persist(infirmerie);
		return null;
	}
	

}
