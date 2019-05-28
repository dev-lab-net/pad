package net.devlab.pad;

import java.net.URI;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;

/**
 * 
 * @author dj0n1
 *
 */
public class App {

    private static final URI SERVER_URI = URI.create("http://0.0.0.0:8080");

    public static void main(String[] args) throws Exception {
        Server server = JettyHttpContainerFactory.createServer(SERVER_URI, new AppConfig(), false);
        final Handler baseHandler = server.getHandler();
        final ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[] { "index.html" });
        resourceHandler.setResourceBase("src/main/webapp");
        final HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(baseHandler);
        server.setHandler(handlers);
        server.start();
        server.join();
    }
}
