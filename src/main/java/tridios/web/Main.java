package tridios.web;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.jboss.weld.environment.servlet.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Main {
    public static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws LifecycleException, ServletException, MalformedURLException {
        final Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.valueOf("8080"));

        final String webappDirLocation = "src/main/resources/";
        final StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());

        final File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        ctx.addApplicationListener(Listener.class.getName());

        //
        Tomcat.addServlet(ctx, "dateServlet", new org.glassfish.jersey.servlet.ServletContainer());
        ctx.addServletMapping("/rest", "dateServlet");


        ContextResource resource = new ContextResource();
        resource.setName("jdbc/db");
        resource.setAuth("Container");
        resource.setType("javax.sql.DataSource");
        resource.setScope("Sharable");
        resource.setProperty("driverClassName",
                "com.mysql.jdbc.Driver");
        resource.setProperty("url", "jdbc:mysql://localhost:3306/javatest");

        ctx.getNamingResources().addResource(resource);

        tomcat.start();
        tomcat.getServer().await();
    }
}
