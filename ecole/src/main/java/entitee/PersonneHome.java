package entitee;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import config.HibernateUtil;

/**
 * Home object for domain model class Personne.
 * @see entite.Personne
 * @author Hibernate Tools
 */
public class PersonneHome {

    private static final Log log = LogFactory.getLog(PersonneHome.class);
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void persist(Personne transientInstance) {
        log.debug("persisting Personne instance");
        try {
            Session ss = sessionFactory.openSession();
            Transaction tx = ss.beginTransaction();
            ss.save(transientInstance);
            tx.commit();
            ss.close();
            log.debug("persist Personne successful");
        } catch (RuntimeException re) {
            log.error("persist Personne failed", re);
            throw re;
        }
    }

    public void remove(Personne persistentInstance) {
        log.debug("removing Personne instance");
        try {
            Session ss = sessionFactory.openSession();
            Transaction tx = ss.beginTransaction();
            ss.delete(persistentInstance);
            tx.commit();
            ss.close();
            log.debug("remove Personne successful");
        } catch (RuntimeException re) {
            log.error("remove Personne failed", re);
            throw re;
        }
    }

    public void merge(Personne detachedInstance) {
        log.debug("merging Personne instance");
        try {
            Session ss = sessionFactory.openSession();
            Transaction tx = ss.beginTransaction();
            ss.update(detachedInstance);
            tx.commit();
            ss.close();
            log.debug("merge Personne successful");
        } catch (RuntimeException re) {
            log.error("merge Personne failed", re);
            throw re;
        }
    }

    public Personne findById(int id) {
        log.debug("getting Personne instance with id: " + id);
        try {
            Session ss = sessionFactory.openSession();
            Transaction tx = ss.beginTransaction();
            Personne personne = ss.get(Personne.class, id);
            log.debug("get Personne successful");
            ss.close();
            return personne;
        } catch (HibernateException e) {
            log.error("get Personne failed", e);
            throw e;
        }
    }
}
