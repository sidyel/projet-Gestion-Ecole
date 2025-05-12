package entitee;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import config.HibernateUtil;

/**
 * Home object for domain model class Salle.
 * @see entite.Salle
 */
public class SalleHome {

    private static final Log log = LogFactory.getLog(SalleHome.class);
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void persist(Salle transientInstance) {
        log.debug("persisting Salle instance");
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(transientInstance);
            tx.commit();
            session.close();
            log.debug("persist successful");
        } catch (HibernateException e) {
            log.error("persist failed", e);
            throw e;
        }
    }

    public void remove(Salle persistentInstance) {
        log.debug("removing Salle instance");
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(persistentInstance);
            tx.commit();
            session.close();
            log.debug("remove successful");
        } catch (HibernateException e) {
            log.error("remove failed", e);
            throw e;
        }
    }

    public Salle merge(Salle detachedInstance) {
        log.debug("merging Salle instance");
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Salle result = (Salle) session.merge(detachedInstance);
            tx.commit();
            session.close();
            log.debug("merge successful");
            return result;
        } catch (HibernateException e) {
            log.error("merge failed", e);
            throw e;
        }
    }

    public Salle findById(int id) {
        log.debug("getting Salle instance with id: " + id);
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Salle instance = (Salle) session.get(Salle.class, id);
            tx.commit();
            session.close();
            log.debug("get successful");
            return instance;
        } catch (HibernateException e) {
            log.error("get failed", e);
            throw e;
        }
    }
}
