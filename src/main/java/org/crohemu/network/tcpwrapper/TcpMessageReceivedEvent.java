package org.crohemu.network.tcpwrapper;

public class TcpMessageReceivedEvent {
    TcpMessage tcpMessage;
    TcpClient source;

    public TcpMessageReceivedEvent(TcpMessage tcpMessage, TcpClient source) {
        this.tcpMessage = tcpMessage;
        this.source = source;
    }

    public TcpMessage getTcpMessage() {
        return tcpMessage;
    }

    public TcpClient getSource() {
        return source;
    }
}
