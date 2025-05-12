package bean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class NavigationBean {

    public String goToPrescolaire() {
        return "prescolaire"; // Redirige vers prescolaire.xhtml
    }

    public String goToElementaire() {
        return "elementaire"; // Redirige vers elementaire.xhtml
    }

    public String goToMoyen() {
        return "moyen"; // Redirige vers moyen.xhtml
    }

    public String goToLycee() {
        return "lycee"; // Redirige vers lycee.xhtml
    }
}
