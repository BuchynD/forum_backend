package ua.cn.stu.dao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import ua.cn.stu.domain.*;

import java.util.List;

public class BadWordDAO {

    private Session session;

    // Constructor
    public BadWordDAO(Session session) {
        this.session = session;
    }

    // Save a new BadWord
    public void create(BadWord badWord) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(badWord);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Find a BadWord by ID
    public BadWord findById(Long id) {
        return (BadWord) session.get(BadWord.class, id);
    }

    // Find a BadWord by word
    public BadWord findByWord(String word) {
        Query query = session.createQuery("FROM BadWord WHERE word = :word");
        query.setParameter("word", word);
        return (BadWord) query.uniqueResult();
    }

    // Get all BadWords
    public List<BadWord> findAll() {
        return session.createQuery("FROM BadWord").list();
    }

    // Update a BadWord
    public void update(BadWord badWord) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(badWord);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Delete a BadWord
    public void delete(BadWord badWord) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(badWord);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
