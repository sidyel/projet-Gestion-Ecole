package bean;

import javax.faces.bean.ManagedBean;

import entitee.Cours;
import entitee.CoursHome;

@ManagedBean
public class CoursBean {
	
	Cours cours=new Cours();
	CoursHome coursHome=new CoursHome();
	public Cours getCours() {
		return cours;
	}
	public void setCours(Cours cours) {
		this.cours = cours;
	}
	
	CoursHome courshome=new CoursHome();
	
	public String ajouterCours()
	{
		courshome.persist(cours);
		return null;
	}

}
