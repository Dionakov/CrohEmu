package org.crohemu.network.tcpwrapper.handler;

import org.crohemu.network.tcpwrapper.TcpClient;

import java.io.IOException;

@FunctionalInterface
public interface TcpDataHandler {

    void handleTcpData(TcpClient client) throws IOException;
}
