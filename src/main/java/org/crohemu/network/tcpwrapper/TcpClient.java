package org.crohemu.network.tcpwrapper;

import java.io.IOException;
import java.net.Socket;

public class TcpClient {
    Socket socket;

    public TcpClient(Socket socket) throws IOException {
        this.socket = socket;
    }

    public void send(TcpMessage message) throws IOException {
        message.serialize();
        byte[] messageContent = message.getRawContent();
        socket.getOutputStream().write(messageContent, 0, messageContent.length);
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public Socket getSocket() {
        return socket;
    }
}
