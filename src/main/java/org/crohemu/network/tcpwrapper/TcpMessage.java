package org.crohemu.network.tcpwrapper;

import java.util.Date;

/**
 * TCP message POJO
 */
public class TcpMessage {

    byte[] content = null;
    Date timestamp = null;

    public TcpMessage(byte[] content, Date timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }

    public byte[] getContent() {
        return content;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
