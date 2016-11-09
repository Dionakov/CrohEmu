package org.crohemu.network.tcpwrapper.impl;

import org.crohemu.network.tcpwrapper.TcpClient;
import org.crohemu.network.tcpwrapper.TcpDataHandler;
import org.crohemu.network.tcpwrapper.TcpServer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TcpServerImpl implements TcpServer {
    ServerSocket serverSocket;

    List<TcpClient> clients = new ArrayList<>();

    @Override
    public void start(InetAddress ipAddress, int portNumber, TcpDataHandler dataHandler) throws IOException {
        serverSocket = new ServerSocket(portNumber, Integer.MAX_VALUE, ipAddress);

            new Thread(() -> {
                try {
                    Socket clientSocket = serverSocket.accept();
                    TcpClient newClient = new TcpClient(clientSocket);
                    clients.add(newClient);
                    new Thread(() -> {
                        try {
                            dataHandler.handleTcpData(clientSocket.getInputStream());
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    }).start();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }).start();
    }
}
