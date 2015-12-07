package tridios.web;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.text.MessageFormat;

@WebServlet(name = "simple", value = {"/simple"}, asyncSupported = true)
public class TestServlet extends HttpServlet {

    /**
     * Simply spawn a new thread (from the app server's pool) for every new async request.
     * Will consume a lot more threads for many concurrent requests.
     * Shows warning but can be ignored will create no mem leak as resources are shared
     */
    public void service(ServletRequest req, final ServletResponse res)
            throws ServletException, IOException {

        // create the async context, otherwise getAsyncContext() will be null
        final AsyncContext ctx = req.startAsync();

        // set the timeout
        ctx.setTimeout(30000);
        // attach listener to respond to lifecycle events of this AsyncContext
        ctx.addListener(new AsyncListener() {
            public void onComplete(AsyncEvent event) throws IOException {
                log("onComplete called");
            }

            public void onTimeout(AsyncEvent event) throws IOException {
                log("onTimeout called");
            }

            public void onError(AsyncEvent event) throws IOException {
                log("onError called");
            }

            public void onStartAsync(AsyncEvent event) throws IOException {
                log("onStartAsync called");
            }
        });

        // spawn some task in a background thread
        ctx.start(new Runnable() {
            public void run() {

                try {
                    ctx.getResponse().getWriter().write(
                            MessageFormat.format("<h1>Processing task in bgt_id:[{0}]</h1>",
                                    Thread.currentThread().getId()));
                } catch (IOException e) {
                    log("Problem processing task", e);
                }

                ctx.complete();
            }
        });
    }
}