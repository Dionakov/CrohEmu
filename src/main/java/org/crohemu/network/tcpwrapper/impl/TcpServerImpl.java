package org.crohemu.network.tcpwrapper.impl;

import org.crohemu.network.tcpwrapper.TcpClient;
import org.crohemu.network.tcpwrapper.handler.TcpClientConnectionHandler;
import org.crohemu.network.tcpwrapper.handler.TcpDataHandler;
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

    TcpDataHandler dataHandler;
    TcpClientConnectionHandler clientConnectionHandler;

    @Override
    public void start(InetAddress ipAddress, int portNumber) throws IOException {
        serverSocket = new ServerSocket(portNumber, Integer.MAX_VALUE, ipAddress);

            new Thread(() -> {
                try {
                    Socket clientSocket = serverSocket.accept();
                    TcpClient newClient = new TcpClient(clientSocket);
                    clients.add(newClient);
                    this.clientConnectionHandler.onClientConnected(newClient);
                    new Thread(() -> {
                        try {
                            dataHandler.handleTcpData(newClient);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    }).start();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }).start();
    }

    @Override
    public void setDataHandler(TcpDataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    @Override
    public void setClientConnectionHandler(TcpClientConnectionHandler tcpClientConnectionHandler) {
        this.clientConnectionHandler = tcpClientConnectionHandler;
    }
}
