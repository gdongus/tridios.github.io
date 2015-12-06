package tridios.web;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class JerseyResourceLoader extends Application{

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();

        // register root resource
        classes.add(Rest.class);
        return classes;
    }
}
