package entitee;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import config.HibernateUtil;

/**
 * Home object for domain model class Tuteur.
 * @see entite.Tuteur
 */
public class TuteurHome {

    private static final Log log = LogFactory.getLog(TuteurHome.class);
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void persist(Tuteur transientInstance) {
        log.debug("persisting Tuteur instance");
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

    public void remove(Tuteur persistentInstance) {
        log.debug("removing Tuteur instance");
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

    public Tuteur merge(Tuteur detachedInstance) {
        log.debug("merging Tuteur instance");
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Tuteur result = (Tuteur) session.merge(detachedInstance);
            tx.commit();
            session.close();
            log.debug("merge successful");
            return result;
        } catch (HibernateException e) {
            log.error("merge failed", e);
            throw e;
        }
    }

    public Tuteur findById(int id) {
        log.debug("getting Tuteur instance with id: " + id);
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Tuteur instance = (Tuteur) session.get(Tuteur.class, id);
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
