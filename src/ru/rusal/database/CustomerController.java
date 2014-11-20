package ru.rusal.database;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 * Main class for work with database.
 *
 * Class performs database operations and returns the converted
 * records from tables to objects.
 *
 * To work with the database on the server must be configured JTA
 * resource accessible by "java:comp/env/rusal_test_datasource".
 *
 * @author melges
 */
public class CustomerController {
    private EntityManager em = Persistence.createEntityManagerFactory("UserDataSource").createEntityManager();

    /**
     * Checks correctness of the login-password pair.
     * @param login user login.
     * @param password user password.
     * @return true if user with specified login has specified password, false in other cases include
     * situation when no user with specified login in database.
     */
    public boolean verifyPassword(String login, String password) {
        TypedQuery<User> query = em.createNamedQuery("getUserByLogin", User.class);
        query.setParameter("login", login);
        try {
            User user = query.getSingleResult();
            return user.getPasswordHash().equals(User.calculateHash(login, password));
        } catch (NoResultException e) {
            return false;
        }
    }

    /**
     * Search user with equal login in database and return it.
     * @param login user name which should be searched.
     * @return Founded user object or null.
     */
    public User getUserByLogin(String login) {
        TypedQuery<User> query = em.createNamedQuery("getUserByLogin", User.class);
        query.setParameter("login", login);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Check that user with specified login exist in database.
     * @param login user name which should be checked.
     * @return true if user exist, otherwise - false.
     */
    public boolean isUserExist(String login) {
        TypedQuery<User> query = em.createNamedQuery("getUserByLogin", User.class);
        query.setParameter("login", login);
        try {
            query.getSingleResult();
            return true;
        } catch(NoResultException e) {
            return false;
        }
    }

    /**
     * Flush user to database.
     * @param user object which should be saved.
     */
    public void persist(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }
}
