package bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import entitee.Classes;
import entitee.ClassesHome;
import metier.Operation;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class EmploiDuTempsBean {

    private int selectedClasseId;
    private List<Object[]> emploiDuTemps;
    private List<Classes> classesList = new ArrayList<>();
    private Classes classes = new Classes();
    private ClassesHome classesHome = new ClassesHome();
    private Operation op = new Operation();
    private String role; // Ajout de l'attribut pour le rôle

    // Getters et Setters
    public List<Classes> getClassesList() {
        return classesList;
    }

    public void setClassesList(List<Classes> classesList) {
        this.classesList = classesList;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public int getSelectedClasseId() {
        return selectedClasseId;
    }

    public void setSelectedClasseId(int selectedClasseId) {
        this.selectedClasseId = selectedClasseId;
    }

    public List<Object[]> getEmploiDuTemps() {
        return emploiDuTemps;
    }

    public void setEmploiDuTemps(List<Object[]> emploiDuTemps) {
        this.emploiDuTemps = emploiDuTemps;
    }

    @PostConstruct
    private void init() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            role = (String) externalContext.getSessionMap().get("role");

            if ("ELEVE".equals(role)) {
                // Récupérer la liste des classes de l'élève stockée dans la session
                List<Classes> classesEleve = (List<Classes>) externalContext.getSessionMap().get("classesEleve");

                if (classesEleve != null && !classesEleve.isEmpty()) {
                    // Charger toutes les classes de l'élève
                    classesList.clear();
                    classesList.addAll(classesEleve); // Ajouter toutes les classes de l'élève
                    selectedClasseId = classesEleve.get(0).getIdclasse(); // Préréglez la première classe de l'élève
                    System.out.println("Première classe de l'élève : " + classesEleve.get(0).getNom());
                } else {
                    System.err.println("Aucune classe trouvée pour cet élève.");
                }
            } else {
                // Cas pour les responsables : charger les classes en fonction du cycle
                String cycle = determineCycleFromRole(role);
                classesList = op.listeClasseByCycle(cycle);
            }

            // Debug
            System.out.println("Classes loaded: " + classesList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // Méthode pour récupérer l'emploi du temps de la classe sélectionnée
    public void chargerEmploiDuTemps() {
        classes = classesHome.findById(selectedClasseId);
        emploiDuTemps = op.getEmploiDuTempsParClasse(selectedClasseId);
    }

    // Méthode pour déterminer le cycle à partir du rôle
    private String determineCycleFromRole(String role) {
        if ("PRINCIPAL".equals(role)) {
            return "moyen"; // Remplacez par le cycle approprié
        } else if ("DIRECTEUR".equals(role)) {
            return "elementaire"; // Remplacez par le cycle approprié
        }
     else if ("PROVISEUR".equals(role)) {
        return "lycee"; // Remplacez par le cycle approprié
    }
        // Ajoutez d'autres conditions selon vos besoins
        return "elementaire"; // Cycle par défaut
    }
}
