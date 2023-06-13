package org.example.repositories;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.models.User;
import org.hibernate.Session;

import java.util.List;

/**
 * User's repository with Hibernate
 */
public class UserRepository extends HibernateRepository<User> {
    public UserRepository() {
        tableName = "users";
        aClass = User.class;
        getSessionFactory();
    }

    public User getByLogin(String login) {
        try (Session session = getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root).where(cb.equal(root.get("login"), login));

            Query query = session.createQuery(cr);
            query.setMaxResults(1);
            return (User) query.getResultList().get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
