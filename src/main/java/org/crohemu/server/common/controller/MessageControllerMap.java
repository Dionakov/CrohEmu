package org.crohemu.server.common.controller;

import org.crohemu.network.d2protocol.message.messages.D2MessageType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public abstract class MessageControllerMap {
    private Map<D2MessageType, MessageController> routes = new HashMap<>();

    protected void addRoute(D2MessageType type, MessageController controller) {
        routes.put(type, controller);
    }

    public MessageController get(D2MessageType type) {
        return routes.get(type);
    }
}
