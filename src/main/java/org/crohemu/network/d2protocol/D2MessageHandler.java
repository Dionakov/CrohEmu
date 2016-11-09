package org.crohemu.network.d2protocol;

import org.crohemu.network.d2protocol.message.D2Message;

@FunctionalInterface
public interface D2MessageHandler {
    void handleD2Message(D2Message message);
}
