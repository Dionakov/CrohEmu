package org.crohemu;

import org.apache.log4j.BasicConfigurator;
import org.crohemu.config.BeanConfig;
import org.crohemu.network.d2protocol.D2MessageHandler;
import org.crohemu.network.d2protocol.D2TcpDataHandler;
import org.crohemu.network.tcpwrapper.TcpServer;
import org.crohemu.network.tcpwrapper.impl.TcpServerImpl;
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

        D2MessageHandler d2AuthServerMessageHandler = applicationContext.getBean("d2AuthServerMessageHandler", D2MessageHandler.class);
        D2MessageHandler d2WorldServerMessageHandler = applicationContext.getBean("d2WorldServerMessageHandler", D2MessageHandler.class);

        TcpServer tcpServer = new TcpServerImpl();
        tcpServer.start(InetAddress.getByName("127.0.0.1"), 11234, new D2TcpDataHandler(d2AuthServerMessageHandler));
    }
}
