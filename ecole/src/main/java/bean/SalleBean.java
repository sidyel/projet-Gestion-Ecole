package bean;

import javax.faces.bean.ManagedBean;


import entitee.Salle;
import entitee.SalleHome;

@ManagedBean
public class SalleBean {
	
	Salle salle=new Salle();
	SalleHome sallehome=new  SalleHome();
	public Salle getSalle() {
		return salle;
	}
	public void setSalle(Salle salle) {
		this.salle = salle;
	}
	
	public String ajouterSalle()
	{
		sallehome.persist(salle);
		return null;
	}
	 

}
