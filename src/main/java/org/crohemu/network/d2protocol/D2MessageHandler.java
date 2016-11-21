package org.crohemu.network.d2protocol;

import org.crohemu.network.d2protocol.message.D2Message;
import org.springframework.stereotype.Component;

@FunctionalInterface
@Component
public interface D2MessageHandler {
    void handleD2Message(D2Message message);
}
