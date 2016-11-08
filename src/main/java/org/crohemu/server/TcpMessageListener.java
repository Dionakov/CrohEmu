package org.crohemu.server;

import java.util.EventListener;

/**
 * Listener for TcpMessageReceivedEvent events.
 */
@FunctionalInterface
public interface TcpMessageListener extends EventListener {
    void onTcpMessageReceivedEvent(TcpMessageReceivedEvent tcpMessageEvent);
}
