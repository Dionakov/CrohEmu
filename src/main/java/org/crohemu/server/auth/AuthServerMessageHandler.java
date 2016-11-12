package org.crohemu.server.auth;

import org.crohemu.network.d2protocol.D2MessageHandler;
import org.crohemu.network.d2protocol.message.D2Message;
import org.crohemu.server.common.controller.MessageControllerMap;

import javax.annotation.Resource;
import java.io.IOException;

public class AuthServerMessageHandler implements D2MessageHandler {

    @Resource(name = "authServerMessageControllerMap")
    MessageControllerMap messageControllerMap;

    @Override
    public void handleD2Message(D2Message message) {

        try {
            messageControllerMap.get(message.getType()).handleMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
