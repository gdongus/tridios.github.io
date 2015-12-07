package tridios.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tridios.web.models.BaseModel;
import tridios.web.models.Foo;

import javax.inject.Inject;
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

    @Inject
    private EntityManager em;

    @GET
    @Path("widget")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public List<Foo> sayHello() throws Exception {
        TypedQuery query = em.createQuery("SELECT f FROM Foo f where f.id <= :id ", Foo.class);
        query.setParameter("id", 23L);
        List<Foo> list = query.getResultList();

        return list;
    }

    @GET
    @Path("widget/{name}")
    public String getWidgetById(@PathParam("name") String name) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();

            final Foo foo = new Foo();
            foo.setName(name);

            final BaseModel baseModel = new BaseModel();
            foo.setBaseModel(baseModel);

            em.persist(foo);
            tx.commit();

            LOG.info("Persisted: " + foo.getId());
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e; // or display error message
        } finally {
            em.close();
        }

        return "Hello World from Tomcat Embedded with Jersey!";
    }
}
