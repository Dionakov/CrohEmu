package org.crohemu.server.auth.impl;

import org.crohemu.server.auth.AuthClientHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Croh on 02/11/2016.
 */
public class AuthClientHandlerImpl implements AuthClientHandler {
    List<Socket> clientSockets = new ArrayList<>();

    public AuthClientHandlerImpl() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                clientSockets.forEach(clientSocket -> clientSocket.close());
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
        private Socket clientSocker;
        private DataOutputStream socketOut;
        private BufferedReader socketIn;

        public ClientHandlerRunnable(Socket clientSocket) throws IOException {
            this.clientSocker = clientSocket;
            this.socketOut = new DataOutputStream(clientSocker.getOutputStream());
            this.socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }

        @Override
        public void run() {
            String inputString;
            try {
                socketOut.write(new byte[] {0x00, 0x05, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, 0, 11);
                while ((inputString = socketIn.readLine()) != null) {
                    System.out.println(inputString);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
