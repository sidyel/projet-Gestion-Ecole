package entitee;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import config.HibernateUtil;

/**
 * Home object for domain model class Personnel.
 * @see entite.Personnel
 */
public class PersonnelHome {

    private static final Log log = LogFactory.getLog(PersonnelHome.class);
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void persist(Personnel transientInstance) {
        log.debug("persisting Personnel instance");
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

    public void remove(Personnel persistentInstance) {
        log.debug("removing Personnel instance");
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

    public Personnel merge(Personnel detachedInstance) {
        log.debug("merging Personnel instance");
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Personnel result = (Personnel) session.merge(detachedInstance);
            tx.commit();
            session.close();
            log.debug("merge successful");
            return result;
        } catch (HibernateException e) {
            log.error("merge failed", e);
            throw e;
        }
    }

    public Personnel findById(int id) {
        log.debug("getting Personnel instance with id: " + id);
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Personnel instance = (Personnel) session.get(Personnel.class, id);
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
