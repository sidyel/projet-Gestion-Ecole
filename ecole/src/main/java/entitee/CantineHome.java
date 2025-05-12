package entitee;
// Generated 28 ao�t 2024, 01:06:37 by Hibernate Tools 4.3.6.Final

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import config.HibernateUtil;

/**
 * Home object for domain model class Cantine.
 * 
 * @see entite.Cantine
 * @author Hibernate Tools
 */
public class CantineHome {

	private static final Log log = LogFactory.getLog(CantineHome.class);
	private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	/*
	 * @PersistenceContext private EntityManager entityManager;
	 */

	public void persist(Cantine transientInstance) {
		log.debug("persisting Utilisateur instance");
		try {
			Session ss = sessionFactory.openSession();
			Transaction tx = ss.beginTransaction();
			ss.save(transientInstance);
			tx.commit();
			ss.close();
			log.debug("persist candidat successful");
			// ss.close();
		} catch (RuntimeException re) {
			log.error("persist candidat failed", re);
			throw re;
		}

	}

	public void remove(Cantine persistentInstance) {
		log.debug("removing Utilisateur instance");
		try {
			Session ss = sessionFactory.openSession();
			Transaction tx = ss.beginTransaction();
			ss.delete(persistentInstance);
			tx.commit();
			ss.close();
			log.debug("delete candidat successful");
			ss.close();
		} catch (RuntimeException re) {
			log.error("delete candidat failed", re);
			throw re;
		}

	}

	public void merge(Cantine detachedInstance) {
		log.debug("merging Utilisateur instance");
		try {
			Session ss = sessionFactory.openSession();
			Transaction tx = ss.beginTransaction();
			ss.update(detachedInstance);
			tx.commit();
			ss.close();
			log.debug("update Agentcomptable successful");
			// ss.close();
		} catch (RuntimeException re) {
			log.error("update Agentcomptable failed", re);
			throw re;
		}

	}

	public Cantine findById(int id) {
		log.debug("Recherche de l'instance Utilisateur avec l'identifiant: " + id);
		try {
			Session ss = sessionFactory.openSession();
			Transaction tx = ss.beginTransaction();
			Cantine utilisateur = ss.get(Cantine.class, id);
			log.debug("Recherche réussie");
			return utilisateur;
		} catch (HibernateException e) {
			log.error("La recherche a échoué", e);
			throw e;
		}
	}

	/*
	 * public Utilisateur findById(int id) {
	 * log.debug("getting Utilisateur instance with id: " + id); try { Utilisateur
	 * instance = entityManager.find(Utilisateur.class, id);
	 * log.debug("get successful"); return instance; } catch (RuntimeException re) {
	 * log.error("get failed", re); throw re; } }
	 */
}
