package entitee;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role", schema = "public")
public class Role {

   
    private int idRole;

    @Column(name = "nom_role", nullable = false, length = 50)
    private String nomRole;

    private Set<Personne> personnes = new HashSet<>();

    // Constructors, getters, setters

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role", unique = true, nullable = false)
    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getNomRole() {
        return nomRole;
    }

    public void setNomRole(String nomRole) {
        this.nomRole = nomRole;
    }

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Personne> getPersonnes() {
        return personnes;
    }

    public void setPersonnes(Set<Personne> personnes) {
        this.personnes = personnes;
    }
}
