package org.crohemu.network.tcpwrapper;

import java.io.IOException;

@FunctionalInterface
public interface TcpDataHandler {

    void handleTcpData(TcpClient client) throws IOException;
}
