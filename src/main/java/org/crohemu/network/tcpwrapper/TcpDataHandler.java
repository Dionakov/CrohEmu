package org.crohemu.network.tcpwrapper;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface TcpDataHandler {

    void handleTcpData(InputStream socketIn) throws IOException;
}
