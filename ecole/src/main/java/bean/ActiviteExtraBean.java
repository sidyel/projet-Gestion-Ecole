package bean;

import javax.faces.bean.ManagedBean;

import entitee.ActiviteExtra;
import entitee.ActiviteExtraHome;

@ManagedBean
public class ActiviteExtraBean {
	
	ActiviteExtra activiteExtra=new ActiviteExtra();

	public ActiviteExtra getActiviteExtra() {
		return activiteExtra;
	}

	public void setActiviteExtra(ActiviteExtra activiteExtra) {
		this.activiteExtra = activiteExtra;
	}	
	
	ActiviteExtraHome activiteExtraHome=new ActiviteExtraHome();
	public String organiserActivite()
	{
		activiteExtraHome.persist(activiteExtra);
		return null;
	}

}
