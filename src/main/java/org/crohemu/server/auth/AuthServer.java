package org.crohemu.server.auth;

import org.crohemu.network.tcpwrapper.TcpDataHandler;
import org.crohemu.network.tcpwrapper.impl.TcpServerImpl;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;

public class AuthServer extends TcpServerImpl {
    @Resource(name = "authServerDataHandler")
    TcpDataHandler authServerDataHandler;

    public void start(InetAddress ipAddress, int portNumber) throws IOException {
        super.start(ipAddress, portNumber, authServerDataHandler);
    }
}
