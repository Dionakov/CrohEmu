package org.crohemu.config;

import org.crohemu.server.auth.AuthClientHandler;
import org.crohemu.server.auth.AuthServer;
import org.crohemu.server.auth.ClientAccepterRunnable;
import org.crohemu.server.auth.impl.AuthClientHandlerImpl;
import org.crohemu.server.auth.impl.AuthServerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * Spring Bean configuration.
 *
 * @author Croh
 */
public class BeanConfig {

    @Bean
    public Logger logger() { return LoggerFactory.getLogger("Main");  }

    @Bean
    public AuthServer authServer() {
        try {
            return new AuthServerImpl();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public AuthClientHandler authClientHandler() { return new AuthClientHandlerImpl(); }

    @Bean
    public ClientAccepterRunnable clientAccepterRunnable() { return new ClientAccepterRunnable(); }
}
