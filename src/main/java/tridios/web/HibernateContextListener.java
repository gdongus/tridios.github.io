package tridios.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HibernateContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        HibernateUtil.getSessionFactory(); // create a factory
    }

    public void contextDestroyed(ServletContextEvent event) {
        HibernateUtil.getSessionFactory().close(); // free resources
    }
}
