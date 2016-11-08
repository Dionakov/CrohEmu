package org.crohemu.server;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface TcpDataHandler {

    void handleTcpData(InputStream socketIn) throws IOException;
}
