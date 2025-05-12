package entitee;

import java.util.List;

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
public class RoleHome {

    private static final Log log = LogFactory.getLog(Role.class);
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void persist(Role transientInstance) {
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

    public void remove(Role persistentInstance) {
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

    public Role merge(Role detachedInstance) {
        log.debug("merging Eleve instance");
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Role result = (Role) session.merge(detachedInstance);
            tx.commit();
            session.close();
            log.debug("merge successful");
            return result;
        } catch (HibernateException e) {
            log.error("merge failed", e);
            throw e;
        }
    }

    public Role findById(int id) {
        log.debug("getting Eleve instance with id: " + id);
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Role instance = session.get(Role.class, id);
            tx.commit();
            session.close();
            log.debug("get successful");
            return instance;
        } catch (HibernateException e) {
            log.error("get failed", e);
            throw e;
        }
    }
    public List<Role> findAll() {
        log.debug("retrieving all Role instances");
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            List<Role> roles = session.createQuery("from Role", Role.class).list();
            tx.commit();
            session.close();
            log.debug("retrieve all successful");
            return roles;
        } catch (HibernateException e) {
            log.error("retrieve all failed", e);
            throw e;
        }
    }
}

