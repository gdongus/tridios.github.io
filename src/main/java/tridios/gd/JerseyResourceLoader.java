package tridios.gd;

import org.reflections.Reflections;
import tridios.gd.web.Rest;

import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class JerseyResourceLoader extends Application{

    @Override
    public Set<Class<?>> getClasses() {
        final Reflections reflections = new Reflections("tridios.gd.web");
        final Set<Class<? extends Object>> allClasses =
                reflections.getTypesAnnotatedWith(Path.class);

        return allClasses;
    }
}
