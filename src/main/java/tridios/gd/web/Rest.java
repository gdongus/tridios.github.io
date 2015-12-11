package tridios.gd.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tridios.gd.EMF;
import tridios.gd.models.BaseModel;
import tridios.gd.models.Foo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class Rest {
    public static Logger LOG = LoggerFactory.getLogger(Rest.class);

    @GET
    @Path("widget")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Foo> sayHello() throws Exception {
        List<Foo> response = null;
        final EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();

            TypedQuery query = entityManager.createQuery("SELECT f FROM Foo f where f.id <= :id ", Foo.class);
            query.setParameter("id", 23L);
            response = query.getResultList();

        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            LOG.error(e.getLocalizedMessage(), e);
        } finally {
            if (tx != null && tx.isActive()) tx.commit();
            entityManager.close();
        }
        return response;
    }

    @GET
    @Path("widget/{name}")
    public String getWidgetById(@PathParam("name") String name) {
        EntityTransaction tx = null;
        final EntityManager entityManager = EMF.createEntityManager();

        try {
            tx = entityManager.getTransaction();
            tx.begin();

            final Foo foo = new Foo();
            foo.setName(name);

            final BaseModel baseModel = new BaseModel();
            foo.setBaseModel(baseModel);

            entityManager.persist(foo);
            tx.commit();

            LOG.info("Persisted: " + foo.getId());
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            LOG.error(e.getLocalizedMessage(), e);
        }finally {
            if (tx != null && tx.isActive()) tx.commit();
            entityManager.close();
        }

        return "Hello World from Tomcat Embedded with Jersey!";
    }
}
