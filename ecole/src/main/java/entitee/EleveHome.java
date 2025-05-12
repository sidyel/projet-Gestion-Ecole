package entitee;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import config.HibernateUtil;

/**
 * Home object for domain model class Eleve.
 * @see entite.Eleve
 */
public class EleveHome {

    private static final Log log = LogFactory.getLog(EleveHome.class);
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void persist(Eleve transientInstance) {
        log.debug("persisting Eleve instance");
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

    public void remove(Eleve persistentInstance) {
        log.debug("removing Eleve instance");
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

    public Eleve merge(Eleve detachedInstance) {
        log.debug("merging Eleve instance");
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Eleve result = (Eleve) session.merge(detachedInstance);
            tx.commit();
            session.close();
            log.debug("merge successful");
            return result;
        } catch (HibernateException e) {
            log.error("merge failed", e);
            throw e;
        }
    }

    public Eleve findById(int id) {
        log.debug("getting Eleve instance with id: " + id);
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Eleve instance = session.get(Eleve.class, id);
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
