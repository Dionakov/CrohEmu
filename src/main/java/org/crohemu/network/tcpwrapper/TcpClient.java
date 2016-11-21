package org.crohemu.network.tcpwrapper;

import org.crohemu.server.auth.AuthServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class TcpClient {
    Logger logger = LoggerFactory.getLogger(TcpClient.class);

    Socket socket;

    public TcpClient(Socket socket) throws IOException {
        this.socket = socket;
    }

    public void send(TcpMessage message) throws IOException {
        message.serialize();
        byte[] messageContent = message.getRawContent();

        if (AuthServerConfig.execModeLessThan(AuthServerConfig.ExecMode.DEBUG0)) {
            logger.debug("[{}} ---> {}", socket.getInetAddress(), messageContent);
        }
        socket.getOutputStream().write(messageContent, 0, messageContent.length);
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public Socket getSocket() {
        return socket;
    }
}
