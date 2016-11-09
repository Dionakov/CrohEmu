package org.crohemu.network.tcpwrapper;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Very basic TCP server that transmits messages to callbacks
 */
public interface TcpServer {

    /**
     * Starts the server. It begins accepting connections and redirecting messages.
     * @param ipAddress The local ip address
     * @param portNumber The port number to which the server will listen
     * @param
     */
    void start(InetAddress ipAddress, int portNumber, TcpDataHandler dataHandler) throws IOException;
}
