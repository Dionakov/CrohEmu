package org.crohemu.network.tcpwrapper.handler;

import org.crohemu.network.tcpwrapper.TcpClient;

@FunctionalInterface
public interface TcpClientConnectionHandler {
    void onClientConnected(TcpClient client);
}
