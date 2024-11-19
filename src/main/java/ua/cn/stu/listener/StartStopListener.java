package ua.cn.stu.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ua.cn.stu.dao.HibernateDAOFactory;

public class StartStopListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		HibernateDAOFactory.getInstance().closeSession();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		HibernateDAOFactory.getInstance().getSession();
	}

}
