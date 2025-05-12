package metier;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import entitee.ActiviteExtra;
import entitee.AssoClasseCours;
import entitee.AssoEleveEvaluation;
import entitee.Classes;
import entitee.Cours;
import entitee.Ecole;
import entitee.Eleve;
import entitee.Evaluation;
import entitee.Personne;
import entitee.Personnel;
import entitee.Professeur;
import entitee.Role;
import entitee.RoleHome;
import entitee.Salle;
import entitee.Tuteur;

import javax.servlet.http.HttpSession;


public class Operation {

	private static final SessionFactory sessionFactory = config.HibernateUtil.getSessionFactory();

	/*
	 * @PersistenceContext private EntityManager entityManager;
	 */
	public List<Classes> listeClasse() {
		List<Classes> classesList = null; // Initialisez la liste avant le bloc try
		Transaction tx = null;

		try (Session ss = sessionFactory.openSession()) {
			// Démarrez la transaction
			tx = ss.beginTransaction();

			// Créez la requête avec la classe spécifiée
			TypedQuery<Classes> q = ss.createQuery("SELECT p FROM Classes p", Classes.class);

			// Exécutez la requête et récupérez les résultats
			classesList = q.getResultList();

			// Validez la transaction
			tx.commit();
			ss.close();

		} catch (Exception e) {
			// Si une exception survient, effectuez un rollback de la transaction
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}

		return classesList; // Renvoyez la liste des classes, même si elle est vide
	}

	public List<Salle> listeSalle() {
		List<Salle> salleList = null; // Initialisez la liste avant le bloc try
		Transaction tx = null;

		try (Session ss = sessionFactory.openSession()) {
			// Démarrez la transaction
			tx = ss.beginTransaction();

			// Créez la requête avec la classe spécifiée
			TypedQuery<Salle> q = ss.createQuery("SELECT p FROM Salle p", Salle.class);

			// Exécutez la requête et récupérez les résultats
			salleList = q.getResultList();

			// Validez la transaction
			tx.commit();
			ss.close();

		} catch (Exception e) {
			// Si une exception survient, effectuez un rollback de la transaction
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}

		return salleList; // Renvoyez la liste des classes, même si elle est vide
	}

	public List<Cours> listeCours() {
		List<Cours> coursList = null; // Initialisez la liste avant le bloc try
		Transaction tx = null;

		try (Session ss = sessionFactory.openSession()) {
			// Démarrez la transaction
			tx = ss.beginTransaction();

			// Créez la requête avec la classe spécifiée
			TypedQuery<Cours> q = ss.createQuery("SELECT p FROM Cours p", Cours.class);

			// Exécutez la requête et récupérez les résultats
			coursList = q.getResultList();

			// Validez la transaction
			tx.commit();

		} catch (Exception e) {
			// Si une exception survient, effectuez un rollback de la transaction
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}

		return coursList; // Renvoyez la liste des classes, même si elle est vide
	}
	public List<Professeur> listeProfesseur() {
		List<Professeur> profList = null; // Initialisez la liste avant le bloc try
		Transaction tx = null;

		try (Session ss = sessionFactory.openSession()) {
			// Démarrez la transaction
			tx = ss.beginTransaction();

			// Créez la requête avec la classe spécifiée
			TypedQuery<Professeur> q = ss.createQuery("SELECT p FROM Professeur p", Professeur.class);

			// Exécutez la requête et récupérez les résultats
			profList = q.getResultList();

			// Validez la transaction
			tx.commit();

		} catch (Exception e) {
			// Si une exception survient, effectuez un rollback de la transaction
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}

		return profList; // Renvoyez la liste des classes, même si elle est vide
	}

	public Personne connexion(String login) {

		try {
			System.out.println("bonjourghjkl");

			Session ss = sessionFactory.openSession();
			Transaction tx = ss.beginTransaction();
			Query q = ss.createQuery("SELECT p FROM Personne p WHERE p.login = :login ",
					Personne.class);

			q.setParameter("login", login);
			return (Personne) q.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // Gérez le cas où aucune personne n'est trouvée
	}

	public void enregistrerSalleEtCours(Salle salle, Cours cours) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			// Réattacher l'entité avec merge
			Cours mergedCours = (Cours) session.merge(cours);
			Salle mergedSalle = (Salle) session.merge(salle);

			// Ajouter la salle au cours
			mergedSalle.getCourses().add(mergedCours);
			mergedCours.getSalles().add(mergedSalle);

			// Sauvegarder les entités
			session.saveOrUpdate(mergedSalle);
			session.saveOrUpdate(mergedCours);

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void enregistrerClasseEtProf(Classes classe, Professeur professeur) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			// Réattacher l'entité avec merge
			Classes mergedClasse = (Classes) session.merge(classe);
			Professeur mergedPRof = (Professeur) session.merge(professeur);

			// Ajouter la salle au cours
			mergedClasse.getProfesseurs().add(mergedPRof);
			mergedPRof.getClasses().add(mergedClasse);

			// Sauvegarder les entités
			session.saveOrUpdate(mergedPRof);
			session.saveOrUpdate(mergedClasse);

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public List<Eleve> RechercheEleve(String nom, String prenom) {

	    try {
	        System.out.println("Début de la recherche de l'élève...");

	        Session ss = sessionFactory.openSession();
	        Transaction tx = ss.beginTransaction();

	        // Construire dynamiquement la requête en fonction des paramètres fournis
	        StringBuilder queryString = new StringBuilder("SELECT p FROM Eleve p WHERE 1=1");

	        if (nom != null && !nom.isEmpty()) {
	            queryString.append(" AND p.personne.nom LIKE :nom");
	        }
	        if (prenom != null && !prenom.isEmpty()) {
	            queryString.append(" OR p.personne.prenom LIKE :prenom");
	        }

	        TypedQuery<Eleve> q = ss.createQuery(queryString.toString(), Eleve.class);

	        // Définir les paramètres si fournis
	        if (nom != null && !nom.isEmpty()) {
	            q.setParameter("nom", "%" + nom + "%");
	        }
	        if (prenom != null && !prenom.isEmpty()) {
	            q.setParameter("prenom", "%" + prenom + "%");
	        }

	        List<Eleve> eleves = q.getResultList(); // Récupérer tous les résultats correspondants

	        tx.commit();  // Commit la transaction
	        return eleves;  // Retourner la liste des élèves trouvés

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return new ArrayList<>();  // Retourner une liste vide si aucun élève n'est trouvé
	}
	
	public List<Object[]> getEmploiDuTempsParClasse(int idClasse) {
	    try {
	        Session session = sessionFactory.openSession();
	        Transaction tx = session.beginTransaction();

	        // Requête pour récupérer les informations d'emploi du temps et professeur
	        org.hibernate.query.Query<Object[]> query = session.createQuery(
	            "SELECT assoc.date, assoc.heured, assoc.heuref, cours.matiere, prof.personne.nom, prof.personne.prenom " +
	            "FROM AssoClasseCours assoc " +
	            "JOIN assoc.classes classe " +
	            "JOIN assoc.cours cours " +
	            "JOIN cours.professeurs prof " +  // On suppose qu'il y a une relation entre Cours et Professeur
	            "WHERE classe.id = :idClasse",
	            Object[].class
	        );
	        query.setParameter("idClasse", idClasse);

	        List<Object[]> emploiDuTemps = query.getResultList();

	        tx.commit();
	        session.close();
	        return emploiDuTemps;

	    } catch (HibernateException e) {
	    	System.out.println("erreur");
	        throw e;
	    }
	}
	
	public Classes rechercheClasse(String nom) {

		try {
			System.out.println("bonjourghjkl");

			Session ss = sessionFactory.openSession();
			Transaction tx = ss.beginTransaction();
			Query q = ss.createQuery("SELECT p FROM Classes p WHERE p.nom = :login" ,Classes.class);

			q.setParameter("login", nom);
			return (Classes) q.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // Gérez le cas où aucune personne n'est trouvée
	}
	
	public List<Eleve> listeEleveClasse(String nom) {
		List<Eleve> eleveList = null; // Initialisez la liste avant le bloc try
		Transaction tx = null;

		try (Session ss = sessionFactory.openSession()) {
			// Démarrez la transaction
			tx = ss.beginTransaction();

			// Créez la requête avec la classe spécifiée
			TypedQuery<Eleve> q = ss.createQuery("SELECT p FROM Eleve p where p.classes.nom=:nom", Eleve.class);
			q.setParameter("nom", nom);
			// Exécutez la requête et récupérez les résultats
			eleveList = q.getResultList();

			// Validez la transaction
			tx.commit();
			ss.close();

		} catch (Exception e) {
			// Si une exception survient, effectuez un rollback de la transaction
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}

		return eleveList; // Renvoyez la liste des classes, même si elle est vide
	}
	public List<Classes> infosClasse(String nom) {
	    List<Classes> classe = null; // Initialisation de la variable pour stocker le résultat
	    Transaction tx = null; // Initialisation de la transaction

	    try (Session ss = sessionFactory.openSession()) { // Utilisation d'un try-with-resources pour fermer la session automatiquement
	        tx = ss.beginTransaction();
	        Query q = ss.createQuery("SELECT c FROM Classes c WHERE c.nom = :nom", Classes.class);
	        q.setParameter("nom", nom);
	        
	        classe = q.getResultList(); // Récupération du résultat
	        tx.commit(); // Engagement de la transaction
	    } catch (NoResultException e) {
	        // Gérer le cas où aucune classe n'est trouvée
	        System.out.println("Aucune classe trouvée pour le nom : " + nom);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback(); // Rétrogradation de la transaction en cas d'erreur
	        }
	        e.printStackTrace(); // Impression de la trace de l'exception
	    }
	    return classe; // Retourner l'objet Classes ou null si aucune classe n'est trouvée
	}
	
	public List<Personnel> listeChauffeur(String nom) {
	    List<Personnel> chauffeur = null; // Initialisation de la variable pour stocker le résultat
	    Transaction tx = null; // Initialisation de la transaction

	    try (Session ss = sessionFactory.openSession()) { // Utilisation d'un try-with-resources pour fermer la session automatiquement
	        tx = ss.beginTransaction();
	        Query q = ss.createQuery("SELECT c FROM Personnel c WHERE c.role = :nom", Personnel.class);
	        q.setParameter("nom", nom);
	        
	        chauffeur = q.getResultList(); // Récupération du résultat
	        tx.commit(); // Engagement de la transaction
	    } catch (NoResultException e) {
	        // Gérer le cas où aucune classe n'est trouvée
	        System.out.println("Aucune chauffeur trouvée pour le nom : " + nom);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback(); // Rétrogradation de la transaction en cas d'erreur
	        }
	        e.printStackTrace(); // Impression de la trace de l'exception
	    }
	    return chauffeur; // Retourner l'objet Classes ou null si aucune classe n'est trouvée
	}
	
	public List<Evaluation> listeEvauation(String nom) {
	    List<Evaluation> evaluation = null; // Initialisation de la variable pour stocker le résultat
	    Transaction tx = null; // Initialisation de la transaction

	    try (Session ss = sessionFactory.openSession()) { // Utilisation d'un try-with-resources pour fermer la session automatiquement
	        tx = ss.beginTransaction();
	        Query q = ss.createQuery("SELECT c FROM Evaluation c WHERE c.classe.nom = :nom", Evaluation.class);
	        q.setParameter("nom", nom);
	        
	        evaluation = q.getResultList(); // Récupération du résultat
	        tx.commit(); // Engagement de la transaction
	    } catch (NoResultException e) {
	        // Gérer le cas où aucune classe n'est trouvée
	        System.out.println("Aucune chauffeur trouvée pour le nom : " + nom);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback(); // Rétrogradation de la transaction en cas d'erreur
	        }
	        e.printStackTrace(); // Impression de la trace de l'exception
	    }
	    return evaluation; // Retourner l'objet Classes ou null si aucune classe n'est trouvée
	}
	
	@Transactional
	public List<Evaluation> listeEvaluation(String nom) {
	    List<Evaluation> evaluations = null; // Initialisation de la variable pour stocker le résultat
	    Transaction tx = null; // Initialisation de la transaction

	    try (Session ss = sessionFactory.openSession()) { // Utilisation d'un try-with-resources pour fermer la session automatiquement
	        tx = ss.beginTransaction();
	        Query q = ss.createQuery("SELECT c FROM Evaluation c WHERE c.classe.nom = :nom", Evaluation.class);
	        q.setParameter("nom", nom);
	        
	        evaluations = q.getResultList(); // Récupération du résultat
	        tx.commit(); // Engagement de la transaction
	    } catch (NoResultException e) {
	        // Gérer le cas où aucune classe n'est trouvée
	        System.out.println("Aucune evaluation trouvée pour le nom : " + nom);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback(); // Rétrogradation de la transaction en cas d'erreur
	        }
	        e.printStackTrace(); // Impression de la trace de l'exception
	    }
	    return evaluations; // Retourner l'objet Classes ou null si aucune classe n'est trouvée
	}
	
	public List<AssoEleveEvaluation> listeEleveEvaluation(String nom) {
	    List<AssoEleveEvaluation> evaluations = null; 
	    Transaction tx = null; 

	    if (nom == null || nom.trim().isEmpty()) {
	        System.out.println("Le nom de la classe est vide ou nul.");
	        return evaluations; // Retourne une liste nulle si le nom est invalide
	    }

	    try (Session ss = sessionFactory.openSession()) { 
	        tx = ss.beginTransaction();

	        Query q = ss.createQuery("SELECT c FROM AssoEleveEvaluation c WHERE c.eleve.classes.nom = :nom", AssoEleveEvaluation.class);
	        q.setParameter("nom", nom);
	        
	        evaluations = q.getResultList(); 

	        if (evaluations.isEmpty()) {
	            System.out.println("Aucune évaluation trouvée pour la classe : " + nom);
	        }

	        tx.commit(); 

	    } catch (NoResultException e) {
	        System.out.println("Aucune évaluation trouvée pour la classe : " + nom);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback(); 
	        }
	        e.printStackTrace(); 
	    }

	    return evaluations; 
	}

	public List<AssoEleveEvaluation> listeEleveEvaluationParEvaluation(Long idEvaluation) {
	    List<AssoEleveEvaluation> evaluations = null;
	    Transaction tx = null;

	    if (idEvaluation == null) {
	        System.out.println("L'ID de l'évaluation est vide ou nul.");
	        return evaluations; // Retourne une liste nulle si l'ID est invalide
	    }

	    try (Session ss = sessionFactory.openSession()) {
	        tx = ss.beginTransaction();

	        // Requête pour récupérer les élèves associés à une évaluation spécifique
	        Query q = ss.createQuery("SELECT c FROM AssoEleveEvaluation c WHERE c.evaluation.id = :idEvaluation", AssoEleveEvaluation.class);
	        q.setParameter("idEvaluation", idEvaluation);

	        evaluations = q.getResultList();

	        if (evaluations.isEmpty()) {
	            System.out.println("Aucun élève trouvé pour l'évaluation avec l'ID : " + idEvaluation);
	        }

	        tx.commit();

	    } catch (NoResultException e) {
	        System.out.println("Aucun élève trouvé pour l'évaluation avec l'ID : " + idEvaluation);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	    }

	    return evaluations;
	}

	
	
	
	public Role getRole(String nom) {
		Role role = null; // Initialisation de la variable pour stocker le résultat
	    Transaction tx = null; // Initialisation de la transaction

	    try (Session ss = sessionFactory.openSession()) { // Utilisation d'un try-with-resources pour fermer la session automatiquement
	        tx = ss.beginTransaction();
	        Query q = ss.createQuery("SELECT c FROM Role c WHERE c.nomRole = :nom", Role.class);
	        q.setParameter("nom", nom);
	        
	        role = (Role) q.getSingleResult(); // Récupération du résultat
	        tx.commit(); // Engagement de la transaction
	    } catch (NoResultException e) {
	        // Gérer le cas où aucune classe n'est trouvée
	        System.out.println("Aucune evaluation trouvée pour le nom : " + nom);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback(); // Rétrogradation de la transaction en cas d'erreur
	        }
	        e.printStackTrace(); // Impression de la trace de l'exception
	    }
	    return role; // Retourner l'objet Classes ou null si aucune classe n'est trouvée
	}
	
	public Eleve getEleve(int id) {
		Eleve eleve = null; // Initialisation de la variable pour stocker le résultat
	    Transaction tx = null; // Initialisation de la transaction

	    try (Session ss = sessionFactory.openSession()) { // Utilisation d'un try-with-resources pour fermer la session automatiquement
	        tx = ss.beginTransaction();
	        Query q = ss.createQuery("SELECT c FROM Eleve c WHERE c.personne.idPersonne = :nom", Eleve.class);
	        q.setParameter("nom", id);
	        
	        eleve = (Eleve) q.getSingleResult(); // Récupération du résultat
	        tx.commit(); // Engagement de la transaction
	    } catch (NoResultException e) {
	        // Gérer le cas où aucune classe n'est trouvée
	        System.out.println("Aucune evaluation trouvée pour le nom : " + id);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback(); // Rétrogradation de la transaction en cas d'erreur
	        }
	        e.printStackTrace(); // Impression de la trace de l'exception
	    }
	    return eleve; // Retourner l'objet Classes ou null si aucune classe n'est trouvée
	}
	
	public Professeur getProfesseur(int id) {
		Professeur professeur = null; // Initialisation de la variable pour stocker le résultat
	    Transaction tx = null; // Initialisation de la transaction

	    try (Session ss = sessionFactory.openSession()) { // Utilisation d'un try-with-resources pour fermer la session automatiquement
	        tx = ss.beginTransaction();
	        Query q = ss.createQuery("SELECT c FROM Professeur c WHERE c.personne.idPersonne = :nom", Professeur.class);
	        q.setParameter("nom", id);
	        
	        professeur = (Professeur) q.getSingleResult(); // Récupération du résultat
	        tx.commit(); // Engagement de la transaction
	    } catch (NoResultException e) {
	        // Gérer le cas où aucune classe n'est trouvée
	        System.out.println("Aucune evaluation trouvée pour le nom : " + id);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback(); // Rétrogradation de la transaction en cas d'erreur
	        }
	        e.printStackTrace(); // Impression de la trace de l'exception
	    }
	    return professeur; // Retourner l'objet Classes ou null si aucune classe n'est trouvée
	}
	
	public Personnel getPersonnel(int id) {
		Personnel personnel = null; // Initialisation de la variable pour stocker le résultat
	    Transaction tx = null; // Initialisation de la transaction

	    try (Session ss = sessionFactory.openSession()) { // Utilisation d'un try-with-resources pour fermer la session automatiquement
	        tx = ss.beginTransaction();
	        Query q = ss.createQuery("SELECT c FROM Personnel c WHERE c.personne.idPersonne = :nom", Personnel.class);
	        q.setParameter("nom", id);
	        
	        personnel = (Personnel) q.getSingleResult(); // Récupération du résultat
	        tx.commit(); // Engagement de la transaction
	    } catch (NoResultException e) {
	        // Gérer le cas où aucune classe n'est trouvée
	        System.out.println("Aucune evaluation trouvée pour le nom : " + id);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback(); // Rétrogradation de la transaction en cas d'erreur
	        }
	        e.printStackTrace(); // Impression de la trace de l'exception
	    }
	    return personnel; // Retourner l'objet Classes ou null si aucune classe n'est trouvée
	}

	public void verifierUtilisateurConnecte() {
	    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	    if (session != null) {
	        String role = (String) session.getAttribute("role");
	        String login = (String) session.getAttribute("login");

	        if (role != null && login != null) {
	            System.out.println("Utilisateur connecté : " + login + " avec le rôle : " + role);
	        } else {
	            System.out.println("Aucun utilisateur connecté.");
	        }
	    } else {
	        System.out.println("La session est invalide.");
	    }
	}

	public List<Classes> listeClasseByCycle(String cycle) {
	    List<Classes> classesList = null; // Initialisation de la liste
	    Transaction tx = null;

	    try (Session session = sessionFactory.openSession()) {
	        // Démarrage de la transaction
	        tx = session.beginTransaction();

	        // Créez la requête en utilisant le paramètre cycle
	        TypedQuery<Classes> query = session.createQuery(
	            "SELECT c FROM Classes c WHERE c.niveau = :cycle", Classes.class);
	        query.setParameter("cycle", cycle);

	        // Exécutez la requête et récupérez les résultats
	        classesList = query.getResultList();

	        // Validez la transaction
	        tx.commit();
	        session.close();

	    } catch (Exception e) {
	        // Si une exception survient, effectuez un rollback de la transaction
	        if (tx != null) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	    }

	    return classesList; // Renvoyez la liste des classes
	}
	
	public List<String> getMatieresParProfesseur(int idProfesseur) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<String> matieres = new ArrayList<>();

        try {
            tx = session.beginTransaction();
            Professeur professeur = session.get(Professeur.class, idProfesseur);
            if (professeur != null && professeur.getCours() != null) {
                matieres.add(professeur.getCours().getMatiere()); // Récupère la matière du cours
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return matieres;
    }
	
	public List<Classes> getClassesParEleve(int idEleve) {
	    List<Classes> classesList = new ArrayList<>();
	    Transaction tx = null;

	    try (Session session = sessionFactory.openSession()) {
	        tx = session.beginTransaction();

	        // Requête pour récupérer les classes associées à un élève spécifique
	        TypedQuery<Classes> query = session.createQuery(
	            "SELECT e.classes FROM Eleve e WHERE e.idEleve = :idEleve", Classes.class
	        );
	        query.setParameter("idEleve", idEleve);

	        // Obtenez la liste des classes
	        classesList = query.getResultList();

	        tx.commit();
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	    }

	    return classesList;
	}
	public Tuteur getTuteur(int idPersonne) {
	    Transaction tx = null;
	    Tuteur tuteur = null;

	    try (Session ss = sessionFactory.openSession()) {
	        // Démarrer la transaction
	        tx = ss.beginTransaction();

	        // Créez la requête pour récupérer le tuteur et ses élèves
	        TypedQuery<Tuteur> query = ss.createQuery(
	            "SELECT t FROM Tuteur t JOIN FETCH t.personne LEFT JOIN FETCH t.eleves WHERE t.personne.idPersonne = :idPersonne",
	            Tuteur.class
	        );
	        query.setParameter("idPersonne", idPersonne);

	        // Exécutez la requête et récupérez le résultat
	        tuteur = query.getSingleResult();

	        // Validez la transaction
	        tx.commit();
	    } catch (NoResultException e) {
	        System.out.println("Aucun tuteur trouvé pour l'ID de personne : " + idPersonne);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback(); // Annulez la transaction en cas d'erreur
	        }
	        e.printStackTrace();
	    }
	    return tuteur;
	}
	
	public List<Role> getAllRoles() {
		RoleHome rh= new RoleHome();
	    // Récupère tous les rôles disponibles dans la base de données
	    return rh.findAll();
	}

	public List<Eleve> RechercheEleveParNomEtPrenom(String nom, String prenom) {
	    List<Eleve> result = new ArrayList<>();
	    Session session = null;
	    Transaction transaction = null;

	    try {
	        session = sessionFactory.openSession();
	        transaction = session.beginTransaction();

	        // Construire la requête HQL avec des critères de nom et prénom facultatifs
	        StringBuilder queryString = new StringBuilder("SELECT e FROM Eleve e JOIN e.personne p WHERE 1=1");
	        
	        if (nom != null && !nom.isEmpty()) {
	            queryString.append(" AND LOWER(p.nom) LIKE LOWER(:nom)");
	        }
	        if (prenom != null && !prenom.isEmpty()) {
	            queryString.append(" AND LOWER(p.prenom) LIKE LOWER(:prenom)");
	        }

	        Query query = session.createQuery(queryString.toString(), Eleve.class);

	        // Définir les paramètres de la requête si nécessaire
	        if (nom != null && !nom.isEmpty()) {
	            query.setParameter("nom", "%" + nom + "%");
	        }
	        if (prenom != null && !prenom.isEmpty()) {
	            query.setParameter("prenom", "%" + prenom + "%");
	        }

	        // Exécuter la requête
	        result = query.getResultList();
	        transaction.commit();

	    } catch (HibernateException e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }

	    return result;
	}

	public List<Eleve> RechercheEleveParNom(String nom) {
	    List<Eleve> result = new ArrayList<>();
	    Session session = null;
	    Transaction transaction = null;

	    try {
	        session = sessionFactory.openSession();
	        transaction = session.beginTransaction();

	        String queryString = "SELECT e FROM Eleve e JOIN e.personne p WHERE LOWER(p.nom) LIKE LOWER(:nom)";
	        Query query = session.createQuery(queryString, Eleve.class);
	        query.setParameter("nom", "%" + nom + "%");

	        result = query.getResultList();
	        transaction.commit();

	    } catch (HibernateException e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }

	    return result;
	}

	public List<Eleve> RechercheEleveParPrenom(String prenom) {
	    List<Eleve> result = new ArrayList<>();
	    Session session = null;
	    Transaction transaction = null;

	    try {
	        session = sessionFactory.openSession();
	        transaction = session.beginTransaction();

	        String queryString = "SELECT e FROM Eleve e JOIN e.personne p WHERE LOWER(p.prenom) LIKE LOWER(:prenom)";
	        Query query = session.createQuery(queryString, Eleve.class);
	        query.setParameter("prenom", "%" + prenom + "%");

	        result = query.getResultList();
	        transaction.commit();

	    } catch (HibernateException e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }

	    return result;
	}
	
	public List<AssoClasseCours> lesClassesDuProfesseur(int id) {
	    List<AssoClasseCours> classeCours = null;
	    Transaction tx = null;

	    try (Session ss = sessionFactory.openSession()) { // Automatic session closure
	        tx = ss.beginTransaction();

	        // Corrected HQL query with proper parameters
	        Query q = ss.createQuery(
	            "SELECT c FROM AssoClasseCours c WHERE c.professeur.idProfesseur = :nom ", AssoClasseCours.class);
	        
	        q.setParameter("nom", id);
	       

	        classeCours = q.getResultList(); // Get the result list
	        tx.commit(); // Commit transaction
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback(); // Rollback transaction on error
	        }
	        e.printStackTrace(); // Print the stack trace
	    }

	    return classeCours; // Return the list of classes (can be empty if no results)
	}
	
	public List<AssoEleveEvaluation> listeEleveEvaluation(int nom) {
	    List<AssoEleveEvaluation> evaluations = null; 
	    Transaction tx = null; 

	   

	    Session ss = sessionFactory.openSession();
	        tx = ss.beginTransaction();

	        Query q = ss.createQuery("SELECT c FROM AssoEleveEvaluation c WHERE c.evaluation.idEvaluation = :nom", AssoEleveEvaluation.class);
	        q.setParameter("nom", nom);
	        
	        evaluations = q.getResultList(); 

	       

	        tx.commit(); 

	    

	    return evaluations; 
	}
	
	public Double calculerMoyenneGeneraleEleve(Eleve eleve) {
        Double moyenne = null;
        Transaction tx = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            // Récupération des notes pour l'élève donné
            List<Float> notes = session.createQuery(
                "SELECT a.note FROM AssoEleveEvaluation a WHERE a.eleve = :eleve AND a.note IS NOT NULL", Float.class)
                .setParameter("eleve", eleve)
                .getResultList();

            // Calcul de la moyenne si des notes existent
            if (!notes.isEmpty()) {
                double somme = 0;
                for (Float note : notes) {
                    somme += note;
                }
                moyenne = somme / notes.size();
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

        return moyenne;
    }

	public List<ActiviteExtra> listeActivitesExtraScolaires() {
	    List<ActiviteExtra> activitesExtraList = new ArrayList<>();
	    Transaction tx = null;

	    try (Session session = sessionFactory.openSession()) {
	        tx = session.beginTransaction();
	        TypedQuery<ActiviteExtra> query = session.createQuery("FROM ActiviteExtra", ActiviteExtra.class);
	        activitesExtraList = query.getResultList();
	        tx.commit();
	    } catch (HibernateException e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	    }

	    return activitesExtraList;
	}

	
	



	




	
	


}
