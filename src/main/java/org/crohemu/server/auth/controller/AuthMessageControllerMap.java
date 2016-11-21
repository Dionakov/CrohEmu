package org.crohemu.server.auth.controller;

import org.crohemu.server.common.controller.MessageControllerMap;
import org.springframework.stereotype.Component;

import static org.crohemu.network.d2protocol.message.messages.D2MessageType.PROTOCOL_REQUIRED;

@Component
public class AuthMessageControllerMap extends MessageControllerMap {

    public AuthMessageControllerMap() {
        addRoute(PROTOCOL_REQUIRED, message -> {

        });
    }
}
