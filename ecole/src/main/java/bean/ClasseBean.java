package bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import entitee.Classes;
import entitee.ClassesHome;
import metier.Operation;

@ManagedBean
public class ClasseBean {
	
	Classes classe =new Classes();
	List<Classes> classes=new ArrayList<Classes>();
	String nomClasse;
	public List<Classes> getClasses() {
		return classes;
	}
	public void setClasses(List<Classes> classes) {
		this.classes = classes;
	}

	Operation operation=new Operation();
	public String getNomClasse() {
		return nomClasse;
	}
	public void setNomClasse(String nomClasse) {
		this.nomClasse = nomClasse;
	}

	ClassesHome classehome=new ClassesHome();
	public Classes getClasse() {
		return classe;
	}
	public void setClasse(Classes classe) {
		this.classe = classe;
	}
	
	public void rechercherClasse() {
        classes = operation.infosClasse(nomClasse);
        if (classes == null || classes.isEmpty()) {
            System.out.println("Aucune classe trouvée !");
        }
    }
	
	public String save()
	{
		classe.setEffectif(0);
		classehome.persist(classe);
		return null;
	}

}
