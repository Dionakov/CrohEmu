package org.crohemu.server.auth.impl;

import com.sun.corba.se.spi.activation.Server;
import org.crohemu.server.auth.AuthServer;
import org.crohemu.server.auth.AuthServerConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Test auth server.
 *
 * @author Croh
 */
public class TestAuthServer implements AuthServer {

    public static final int PENDING_CONNECTIONS_LIMIT = 300;

    @Autowired
    Logger logger;

    private List<Socket> clients = new ArrayList<>();
    private ServerSocket serverSocket;

    private ClientAccepter clientAccepter;

    public TestAuthServer() throws IOException {
        serverSocket = new ServerSocket(AuthServerConfig.getPortNumber(), PENDING_CONNECTIONS_LIMIT, Inet4Address.getByName(AuthServerConfig.getIpAddress()));
        clientAccepter = new ClientAccepter(serverSocket);
    }

    public void listen() throws IOException {

        logger.info("Auth server now listening on {}@{}", serverSocket.getInetAddress(), serverSocket.getLocalPort());
        Thread accepterThread = new Thread(clientAccepter);
        accepterThread.start();
        try {
            accepterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class ClientAccepter implements Runnable {
        private ServerSocket serverSocket;

        public ClientAccepter(ServerSocket serverSocket) throws IOException {
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    logger.info("Client {} is trying to connect to the auth server", clientSocket.getLocalAddress());
                    clientSocket.close(); // TODO
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
