package org.crohemu.network.d2protocol.message;

public interface D2MessageFactory {
    D2Message createMessage(byte[] messageData);
}
