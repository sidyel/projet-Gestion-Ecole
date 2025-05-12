package entitee;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import config.HibernateUtil;

/**
 * Home object for domain model class Evaluation.
 * @see entite.Evaluation
 */
public class EvaluationHome {

    private static final Log log = LogFactory.getLog(EvaluationHome.class);
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void persist(Evaluation transientInstance) {
        log.debug("persisting Evaluation instance");
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

    public void remove(Evaluation persistentInstance) {
        log.debug("removing Evaluation instance");
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

    public Evaluation merge(Evaluation detachedInstance) {
        log.debug("merging Evaluation instance");
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Evaluation result = (Evaluation) session.merge(detachedInstance);
            tx.commit();
            session.close();
            log.debug("merge successful");
            return result;
        } catch (HibernateException e) {
            log.error("merge failed", e);
            throw e;
        }
    }

    public Evaluation findById(int id) {
        log.debug("getting Evaluation instance with id: " + id);
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            Evaluation instance = session.get(Evaluation.class, id);
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
