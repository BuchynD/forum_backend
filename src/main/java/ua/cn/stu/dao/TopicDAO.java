package ua.cn.stu.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.cn.stu.domain.Topic;
import ua.cn.stu.domain.User;
import ua.cn.stu.domain.Rubric;

import java.util.List;

public class TopicDAO {
    private Session session;

    public TopicDAO(Session session) {
        this.session = session;
    }

    // Create a new Topic
    public void create(Topic topic) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(topic);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Retrieve a Topic by id
    public Topic findById(Long id) {
        return (Topic)session.get(Topic.class, id);
    }

    // Retrieve all Topics
    public List<Topic> findAll() {
        return session.createQuery("from Topic").list();
    }

    // Retrieve all Topics by a specific User
    public List<Topic> findByUser(User user) {
        return session.createQuery("from Topic where author = :author")
                      .setParameter("author", user)
                      .list();
    }

    // Retrieve all Topics in a specific Rubric
    public List<Topic> findByRubric(Rubric rubric) {
        return session.createQuery("from Topic where rubric = :rubric")
                      .setParameter("rubric", rubric)
                      .list();
    }

    // Update an existing Topic
    public void update(Topic topic) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(topic);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Delete a Topic by id
    public void delete(Long id) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Topic topic = (Topic)session.get(Topic.class, id);
            if (topic != null) {
                session.delete(topic);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
