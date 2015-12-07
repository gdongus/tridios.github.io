package tridios.web;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tridios.web.models.BaseModel;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/helloworld")
public class Rest {
    public static Logger LOG = LoggerFactory.getLogger(Rest.class);

    @Inject
    private HibernateUtil emf;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() throws Exception {
        SessionFactory sessionFactory = emf.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            final BaseModel foo = new BaseModel();
            session.save(foo);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return "Hello World from Tomcat Embedded with Jersey!";
    }
}
