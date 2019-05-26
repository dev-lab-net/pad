package net.devlab.pad;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * 
 * @author dj0n1
 *
 */
public class AppConfig extends ResourceConfig {

    public AppConfig() {
        packages("net.devlab.pad.api");
    }
}
