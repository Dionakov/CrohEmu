package org.crohemu.server.common.controller;

import org.crohemu.network.d2protocol.message.D2Message;

import java.io.IOException;

@FunctionalInterface
public interface MessageController {
    void handleMessage(D2Message message) throws IOException;
}
