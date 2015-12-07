package tridios.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tridios.web.models.BaseModel;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/helloworld")
public class Rest {
    public static Logger LOG = LoggerFactory.getLogger(Rest.class);

    @Inject
    private EMF emf;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @SuppressWarnings("unchecked")
    public String sayHello() throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();

            TypedQuery query = em.createQuery("SELECT b FROM BaseModel b where b.id <= :id ", BaseModel.class);
            query.setParameter("id", 23L);
            List<BaseModel> list = query.getResultList();
            final BaseModel baseModel = new BaseModel();
            em.persist(baseModel);
            tx.commit();
        }
        catch (RuntimeException e) {
            if ( tx != null && tx.isActive() ) tx.rollback();
            throw e; // or display error message
        }
        finally {
            em.close();
        }
        return "Hello World from Tomcat Embedded with Jersey!";
    }
}
