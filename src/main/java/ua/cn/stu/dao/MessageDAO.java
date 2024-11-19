package ua.cn.stu.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.cn.stu.domain.Message;
import ua.cn.stu.domain.Topic;
import ua.cn.stu.domain.User;

import java.util.List;

public class MessageDAO {
    private Session session;

    public MessageDAO(Session session) {
        this.session = session;
    }

    // Create a new Message
    public void create(Message message) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Retrieve a Message by id
    public Message findById(Long id) {
        return (Message)session.get(Message.class, id);
    }

    // Retrieve all Messages
    public List<Message> findAll() {
        return session.createQuery("from Message").list();
    }

    // Retrieve all Messages by a specific User
    public List<Message> findByUser(User user) {
        return session.createQuery("from Message where author = :author")
                      .setParameter("author", user)
                      .list();
    }

    // Retrieve all Messages in a specific Topic
    public List<Message> findByTopic(Topic topic) {
        return session.createQuery("from Message where topic = :topic")
                      .setParameter("topic", topic)
                      .list();
    }

    // Update an existing Message
    public void update(Message message) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Delete a Message by id
    public void delete(Long id) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Message message = (Message)session.get(Message.class, id);
            if (message != null) {
                session.delete(message);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
