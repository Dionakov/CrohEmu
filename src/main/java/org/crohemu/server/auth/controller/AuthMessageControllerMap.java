package org.crohemu.server.auth.controller;

import org.crohemu.network.d2protocol.message.messages.login.ProtocolRequiredMessage;
import org.crohemu.server.common.controller.MessageControllerMap;

import static org.crohemu.network.d2protocol.message.messages.D2MessageType.PROTOCOL_REQUIRED;

public class AuthMessageControllerMap extends MessageControllerMap {

    public AuthMessageControllerMap() {

        put(PROTOCOL_REQUIRED, message -> {
            ProtocolRequiredMessage m = new ProtocolRequiredMessage();
            m.setCurrentVersion(1);
            m.setRequiredVersion(2);

            message.getSource().send(m);
        });
    }
}
