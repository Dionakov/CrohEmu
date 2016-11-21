package org.crohemu.server.auth;

import org.crohemu.network.d2protocol.D2MessageHandler;
import org.crohemu.network.d2protocol.message.D2Message;
import org.crohemu.server.auth.controller.AuthMessageControllerMap;
import org.crohemu.server.common.controller.MessageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

import static org.crohemu.server.auth.AuthServerConfig.ExecMode.DEBUG1;
import static org.crohemu.server.auth.AuthServerConfig.ExecMode.RELEASE;
import static org.crohemu.server.auth.AuthServerConfig.execModeLessThan;

@Component
public class AuthServerMessageHandler implements D2MessageHandler {

    Logger logger = LoggerFactory.getLogger(AuthServerMessageHandler.class);

    @Resource
    AuthMessageControllerMap messageControllerMap;

    @Override
    public void handleD2Message(D2Message message) {

        if (execModeLessThan(DEBUG1)) {
            logger.debug("[{}] <--- {}", message.getSource().getSocket().getInetAddress(), message.getRawContent());
        }

        try {
            MessageController controller = messageControllerMap.get(message.getType());
            if (controller == null) {
                if (execModeLessThan(RELEASE)) {
                    logger.error("Controller not defined for message type {}", message.getType());
                }
            } else {
                controller.handleMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
