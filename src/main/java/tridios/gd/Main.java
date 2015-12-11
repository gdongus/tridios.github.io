package tridios.gd;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.jboss.weld.environment.servlet.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.io.File;
import java.net.MalformedURLException;

public class Main {
    public static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws LifecycleException, ServletException, MalformedURLException {
        final Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.valueOf("8080"));

        final String webappDirLocation = "src/main/resources/";
        final StandardContext ctx = (StandardContext) tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());

        final File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        ContextResource resource = new ContextResource();
        resource.setType("javax.sql.DataSource");
        resource.setScope("Sharable");
        resource.setAuth("Container");
        resource.setName("jdbc/db");
        resource.setProperty("driverClassName", "org.postgresql.Driver");
        resource.setProperty("url", "jdbc:postgresql://localhost:5432/test");
        resource.setProperty("username", "gd");
        resource.setProperty("password", "postgres");
        resource.setProperty("defaultTransactionIsolation", "READ_UNCOMMITTED");

        tomcat.enableNaming();
        ctx.getNamingResources().addResource(resource);

        ctx.addApplicationListener(Listener.class.getName());

        tomcat.start();
        tomcat.getServer().await();
    }
}
