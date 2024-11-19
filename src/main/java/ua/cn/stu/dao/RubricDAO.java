package ua.cn.stu.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.cn.stu.domain.Rubric;

import java.util.List;

public class RubricDAO {
    private Session session;

    public RubricDAO(Session session) {
        this.session = session;
    }

    // Create a new Rubric
    public void create(Rubric rubric) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(rubric);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Retrieve a Rubric by id
    public Rubric findById(Long id) {
        return (Rubric)session.get(Rubric.class, id);
    }

    // Retrieve all Rubrics
    public List<Rubric> findAll() {
        return session.createQuery("from Rubric").list();
    }

    // Update an existing Rubric
    public void update(Rubric rubric) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(rubric);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Delete a Rubric by id
    public void delete(Long id) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Rubric rubric = (Rubric)session.get(Rubric.class, id);
            if (rubric != null) {
                session.delete(rubric);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
