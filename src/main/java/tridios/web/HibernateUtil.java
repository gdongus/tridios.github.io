package tridios.web;

import com.mchange.v2.c3p0.AbstractConnectionCustomizer;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;
import tridios.web.models.BaseModel;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class HibernateUtil {
    public static final SessionFactory sessionFactory;

    static {
        try {
            final Configuration configuration = getConfiguration();
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Configuration getConfiguration() {
        Configuration c = new Configuration();

        c.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/javatest");
        c.setProperty("hibernate.connection.username", "root");
        c.setProperty("hibernate.connection.password", "root");
        c.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        c.setProperty("hibernate.connection.autocommit", "true");
        c.setProperty("hibernate.c3p0.autocommit", "true");

        c.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        c.setProperty("cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        c.setProperty("cache.use_query_cache", "true");
        c.setProperty("cache.use_minimal_puts", "false");

        c.setProperty("show_sql", "true");
        c.setProperty("format_sql", "true");
        c.setProperty("hbm2ddl.auto", "create");
        c.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        c.setProperty("hibernate.connection.isolation", "2"); // read commited

        c.setProperty("hibernate.c3p0.min_size", "5");
        c.setProperty("hibernate.c3p0.max_size", "20");
        c.setProperty("hibernate.c3p0.timeout", "300");
        c.setProperty("hibernate.c3p0.max_statements", "50");
        c.setProperty("hibernate.c3p0.idle_test_period", "3000");


        for (Class<?> clazz : new Reflections("tridios.web")
                .getTypesAnnotatedWith(javax.persistence.Entity.class)) {
            c.addAnnotatedClass(clazz);
        }

        return c;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
