package bean;

import javax.faces.bean.ManagedBean;

import entitee.Eleve;
import entitee.EleveHome;
import entitee.Paiement;
import entitee.PaiementHome;

@ManagedBean
public class PaiementBean {
	
	Paiement paiement=new Paiement();
	int eleveId;
	Eleve eleve=new Eleve();

	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}

	public int getEleveId() {
		return eleveId;
	}

	public void setEleveId(int eleveId) {
		this.eleveId = eleveId;
	}

	public Paiement getPaiement() {
		return paiement;
	}

	public void setPaiement(Paiement paiement) {
		this.paiement = paiement;
	}
	
	PaiementHome paiementHome=new PaiementHome();
	EleveHome eleveHome=new EleveHome();
	
	public String effectuerPaiement()
	{
		eleve=eleveHome.findById(eleveId);
		paiement.setEleve(eleve);
		paiementHome.persist(paiement);
		
		return null;
		
	}

}
