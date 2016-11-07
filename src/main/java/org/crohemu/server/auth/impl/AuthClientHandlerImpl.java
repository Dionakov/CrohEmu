package org.crohemu.server.auth.impl;

import org.crohemu.server.auth.AuthClientHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Croh on 02/11/2016.
 */
public class AuthClientHandlerImpl implements AuthClientHandler {
    @Autowired
    Logger logger;

    List<Socket> clientSockets = new ArrayList<>();

    public AuthClientHandlerImpl() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                clientSockets.forEach(clientSocket -> {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }));
    }

    @Override
    public void addClient(Socket clientSocket) {
        clientSockets.add(clientSocket);
        try {
            new Thread(new ClientHandlerRunnable(clientSocket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandlerRunnable implements Runnable {
        private Logger logger = AuthClientHandlerImpl.this.logger;
        private Socket clientSocket;
        private DataOutputStream socketOut;
        private BufferedReader socketIn;

        public ClientHandlerRunnable(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            this.socketOut = new DataOutputStream(this.clientSocket.getOutputStream());
            this.socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }

        @Override
        public void run() {
            String inputString;
            try {
                final byte[] msg = {0x00, 0x05, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
                socketOut.write(msg, 0, msg.length);
                logger.debug("Written {} to socket", msg);
                while ((inputString = socketIn.readLine()) != null) {
                    System.out.println(inputString);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
