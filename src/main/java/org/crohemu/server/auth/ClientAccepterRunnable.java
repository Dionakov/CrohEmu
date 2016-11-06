package org.crohemu.server.auth;

import org.crohemu.server.auth.AuthClientHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Croh on 05/11/2016.
 */
public class ClientAccepterRunnable implements Runnable {
    @Autowired
    Logger logger;

    @Autowired
    private AuthClientHandler authClientHandler;

    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Client {} is trying to connect to the server (remote port {})", clientSocket.getLocalAddress(), clientSocket.getPort());
                authClientHandler.addClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
}
