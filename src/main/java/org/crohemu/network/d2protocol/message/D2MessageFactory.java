package org.crohemu.network.d2protocol.message;

import org.crohemu.network.tcpwrapper.TcpClient;

public interface D2MessageFactory {

    D2Message createMessage(byte[] messageData, TcpClient source);
}
