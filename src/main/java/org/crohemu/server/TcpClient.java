package org.crohemu.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TcpClient {
    Socket socket;
    OutputStream socketOut;

    public TcpClient(Socket socket) throws IOException {
        this.socket = socket;
        this.socketOut = socket.getOutputStream();
    }

    public void send(TcpMessage message) throws IOException {
        byte[] messageContent = message.getContent();
        socketOut.write(messageContent, 0, messageContent.length);
    }

    public void disconnect() throws IOException {
        socket.close();
    }
}
