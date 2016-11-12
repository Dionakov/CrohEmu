package org.crohemu;

import org.apache.log4j.BasicConfigurator;
import org.crohemu.config.BeanConfig;
import org.crohemu.network.d2protocol.D2TcpDataHandler;
import org.crohemu.server.auth.AuthServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Main class of the program.
 *
 * @author Croh
 */
public class CrohEmu {
    public static void main(String[] args) throws IOException {

        // configure log4j
        BasicConfigurator.configure();
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);

        AuthServer authServer = applicationContext.getBean(AuthServer.class);
        authServer.start(InetAddress.getByName("127.0.0.1"), 11234);
    }
}
