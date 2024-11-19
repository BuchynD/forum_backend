package ua.cn.stu.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.cn.stu.domain.User;

import java.util.List;

public class UserDAO {
    private Session session;

    public UserDAO(Session session) {
        this.session = session;
    }

    // Create a new User
    public void create(User user) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Retrieve a User by id
    public User findById(Long id) {
        return (User)session.get(User.class, id);
    }

    // Retrieve all Users
    public List<User> findAll() {
        return session.createQuery("from User").list();
    }

    // Update an existing User
    public void update(User user) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Delete a User by id
    public void delete(Long id) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = (User)session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Find a User by username
    public User findByUsername(String username) {
        return (User)session.createQuery("from User where username = :username")
                      .setParameter("username", username)
                      .uniqueResult();
    }

    // Find a User by email
    public User findByEmail(String email) {
        return (User)session.createQuery("from User where email = :email")
                      .setParameter("email", email)
                      .uniqueResult();
    }
}
