package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import bean.TuteurBean;
import entitee.Eleve;

@FacesConverter(value = "eleveConverter")  // Ajout de value = "eleveConverter"
public class EleveConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        TuteurBean tuteurBean = context.getApplication().evaluateExpressionGet(context, "#{tuteurBean}", TuteurBean.class);
        for (Eleve eleve : tuteurBean.getEleves()) {
            if (String.valueOf(eleve.getIdEleve()).equals(value)) { // Supposons que Eleve ait un champ `id`
                return eleve;
            }
        }
        
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        
        if (value instanceof Eleve) {  // Vérifie que 'value' est bien de type Eleve
            return String.valueOf(((Eleve) value).getIdEleve()); // Remplacez 'getId()' par le getter correct
        } else {
            // Si l'objet n'est pas de type Eleve, renvoie une chaîne vide ou un message d'erreur
            return "";
        }
    }
}
