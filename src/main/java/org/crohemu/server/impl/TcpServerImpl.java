package org.crohemu.server.impl;

import org.crohemu.server.*;

import javax.swing.event.EventListenerList;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TcpServerImpl implements TcpServer {
    EventListenerList eventListenerList = new EventListenerList();

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
//todo move this to dataHandler
   /* @Override
    public void addMessageListener(TcpMessageListener tcpMessageListener) {
        eventListenerList.add(TcpMessageListener.class, tcpMessageListener);
    }

    @Override
    public void removeMessageListener(TcpMessageListener tcpMessageListener) {
        eventListenerList.remove(TcpMessageListener.class, tcpMessageListener);
    }

    private void fireTcpMessageReceivedEvent(TcpMessageReceivedEvent tcpMessageReceivedEvent) {
        Arrays.asList(eventListenerList.getListeners(TcpMessageListener.class))
                .forEach(tcpMessageListener -> tcpMessageListener.onTcpMessageReceivedEvent(tcpMessageReceivedEvent));
    }*/
}
