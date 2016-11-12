package org.crohemu.network;

import org.crohemu.network.tcpwrapper.TcpClient;

import java.io.IOException;
import java.net.Socket;

public class D2Client extends TcpClient {
    public D2Client(Socket socket) throws IOException {
        super(socket);
    }
}
