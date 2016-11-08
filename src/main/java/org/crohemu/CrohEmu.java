package org.crohemu;

import org.apache.log4j.BasicConfigurator;
import org.crohemu.config.BeanConfig;
import org.crohemu.server.TcpServer;
import org.crohemu.server.impl.TcpServerImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;

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

        TcpServer tcpServer = new TcpServerImpl();
        tcpServer.start(InetAddress.getByName("127.0.0.1"), 11234, socketIn -> {
            byte[] buffer = new byte[16];
            while (socketIn.read(buffer) != -1) {
                System.out.println(Arrays.toString(buffer));
                Arrays.fill(buffer, (byte) 0);
            }
        });
    }
}
