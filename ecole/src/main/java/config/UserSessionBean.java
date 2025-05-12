package config;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean(name = "userSessionBean")
@SessionScoped
public class UserSessionBean implements Serializable {

    // Méthode de déconnexion
    public void logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        // Invalidation de la session
        facesContext.getExternalContext().invalidateSession();
        try {
            // Redirection vers la page de connexion
            facesContext.getExternalContext().redirect("connexion.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
