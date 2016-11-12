package org.crohemu.network.tcpwrapper;

import java.util.Objects;

public class TcpMessage {

    protected byte[] rawContent;
    private TcpClient source;

    public TcpMessage(byte[] rawContent, TcpClient source) {
        this.rawContent = rawContent;
        this.source = Objects.requireNonNull(source);
    }

    public TcpMessage() {
    }

    public byte[] getRawContent() {
        return rawContent;
    }

    public TcpClient getSource() {
        return source;
    }

    public void serialize() {

    }
}
