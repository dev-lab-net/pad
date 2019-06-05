package net.devlab.pad;

import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;

import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author dj0n1
 *
 */
@Log4j2
public class App {

    private static URI getURI() {
        Properties properties = new Properties();
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("app.properties")) {
            properties.load(inputStream);
        } catch (Exception e) {
            log.catching(e);
            System.exit(0);
        }
        final String uriString = String.format("%s:%s", properties.get("app.host"), properties.get("app.port"));
        return URI.create(uriString);
    }

    public static void main(String[] args) throws Exception {
        final URI uri = getURI();
        final Server server = JettyHttpContainerFactory.createServer(uri, new AppConfig(), false);
        server.start();
        server.join();
    }
}
