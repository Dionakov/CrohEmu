package org.crohemu.network.d2protocol.message.impl;

import org.crohemu.network.d2protocol.message.D2Message;
import org.crohemu.network.d2protocol.message.D2MessageFactory;
import org.crohemu.network.d2protocol.message.messages.login.ProtocolRequiredMessage;
import org.crohemu.network.tcpwrapper.TcpClient;
import org.crohemu.server.auth.AuthServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class D2MessageFactoryImpl implements D2MessageFactory {

    Logger logger = LoggerFactory.getLogger(D2MessageFactoryImpl.class);
    public static final String INVALID_MESSAGE_ID = "invalid message id";

    @Override
    public D2Message createMessage(byte[] messageData, TcpClient source) {

        int messageId = (messageData[0] << 6) | (messageData[1] >> 2);

        switch (messageId) {
            case 1:
                return new ProtocolRequiredMessage(messageData, source);
            default:

                if (AuthServerConfig.execModeLessThan(AuthServerConfig.ExecMode.DEBUG1)) {
                    logger.error("Invalid packet id : {} \n" +
                            "----- MESSAGE DUMP : {}", messageId, messageData);
                }

                throw new IllegalArgumentException(INVALID_MESSAGE_ID);
        }
    }
}
