package org.crohemu.server.auth.impl;

import org.crohemu.server.auth.AuthServer;
import org.crohemu.server.auth.AuthServerConfig;
import org.crohemu.server.auth.ClientAccepterRunnable;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
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
public class AuthServerImpl implements AuthServer {

    public static final int PENDING_CONNECTIONS_LIMIT = 300;

    @Autowired
    Logger logger;

    @Autowired
    private ClientAccepterRunnable clientAccepter;

    private List<Socket> clients = new ArrayList<>();

    private ServerSocket serverSocket;

    public AuthServerImpl() throws IOException {
        serverSocket = new ServerSocket(AuthServerConfig.getPortNumber(), PENDING_CONNECTIONS_LIMIT, Inet4Address.getByName(AuthServerConfig.getIpAddress()));
    }

    @PostConstruct
    private void init() {
        clientAccepter.setServerSocket(serverSocket);
    }

    public void listen() throws IOException {

        logger.info("Auth server now listening on {}:{}", serverSocket.getInetAddress(), serverSocket.getLocalPort());
        Thread accepterThread = new Thread(clientAccepter);
        accepterThread.start();
        try {
            accepterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
