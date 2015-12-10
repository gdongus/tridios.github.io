ckage tridios.web;


        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

        import javax.naming.Context;
        import javax.naming.InitialContext;
        import javax.sql.DataSource;
        import javax.ws.rs.GET;
        import javax.ws.rs.Path;
        import javax.ws.rs.Produces;
        import javax.ws.rs.core.MediaType;
        import java.sql.Connection;

@Path("/helloworld")
public class Rest {
    public static Logger LOG = LoggerFactory.getLogger(Rest.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        Connection conn = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/db");

            conn = ds.getConnection();
            conn.createStatement();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage(), e);
        }

        return "Hello World from Tomcat Embedded with Jersey!";
    }
}
