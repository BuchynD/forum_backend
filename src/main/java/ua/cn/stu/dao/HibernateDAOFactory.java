package ua.cn.stu.dao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import ua.cn.stu.domain.*;
public class HibernateDAOFactory {
	private static HibernateDAOFactory instance;
	private UserDAO userDAO;
	private TopicDAO topicDAO;
	private MessageDAO messageDAO;
	private RubricDAO rubricDAO;
	private BadWordDAO badWordDAO;
	private Session session;
	//Ініціалізація синглетону
	public static HibernateDAOFactory getInstance() {
		if (null == instance) {
			instance = new HibernateDAOFactory();
		}
		return instance;
	}//Створення об’єкта Session для взаємодії з Hibernate
	public Session getSession() {
		if (null == session) {
			Configuration configuration = new Configuration();
			configuration.setProperty(Environment.DRIVER, "org.postgresql.Driver");
			configuration.setProperty(Environment.URL,
					"jdbc:postgresql://localhost:5432/forum");
			configuration.setProperty(Environment.USER, "postgres");
			configuration.setProperty(Environment.PASS, "\u0066\u006F\u0064\u0061\u0079\u006F\u0075\u0073\u0068\u0069\u0070\u0031\u0031");
			configuration.setProperty(Environment.DIALECT,
					"org.hibernate.dialect.PostgreSQLDialect");
			configuration.setProperty(Environment.HBM2DDL_AUTO, "validate");
			configuration.setProperty(Environment.SHOW_SQL, "true");
			configuration.addAnnotatedClass(User.class);
			configuration.addAnnotatedClass(Topic.class);
			configuration.addAnnotatedClass(Message.class);
			configuration.addAnnotatedClass(Rubric.class);
			configuration.addAnnotatedClass(BadWord.class);
			StandardServiceRegistryBuilder serviceRegistryBuilder =
					new StandardServiceRegistryBuilder();
			serviceRegistryBuilder.applySettings(configuration.getProperties());
			ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
			SessionFactory sessionFactory =
					configuration.buildSessionFactory(serviceRegistry);
			session = sessionFactory.openSession();
		}
		return session;
	}
	public UserDAO getUserDAO() {
	    if (null == userDAO) {
	        userDAO = new UserDAO(getSession());
	    }
	    return userDAO;
	}

	public TopicDAO getTopicDAO() {
	    if (null == topicDAO) {
	        topicDAO = new TopicDAO(getSession());
	    }
	    return topicDAO;
	}

	public MessageDAO getMessageDAO() {
	    if (null == messageDAO) {
	        messageDAO = new MessageDAO(getSession());
	    }
	    return messageDAO;
	}

	public RubricDAO getRubricDAO() {
	    if (null == rubricDAO) {
	        rubricDAO = new RubricDAO(getSession());
	    }
	    return rubricDAO;
	}

	public BadWordDAO getBadWordDAO() {
	    if (null == badWordDAO) {
	        badWordDAO = new BadWordDAO(getSession());
	    }
	    return badWordDAO;
	}

	public void closeSession() {
		 getSession().close();
		}
}