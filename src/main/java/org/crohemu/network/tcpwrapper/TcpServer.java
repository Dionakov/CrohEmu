package org.crohemu.network.tcpwrapper;

import org.crohemu.network.tcpwrapper.handler.TcpClientConnectionHandler;
import org.crohemu.network.tcpwrapper.handler.TcpDataHandler;

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
     */
    void start(InetAddress ipAddress, int portNumber) throws IOException;

    void setDataHandler(TcpDataHandler dataHandler);

    void setClientConnectionHandler(TcpClientConnectionHandler tcpClientConnectionHandler);
}
