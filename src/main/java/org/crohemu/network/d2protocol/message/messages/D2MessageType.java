package org.crohemu.network.d2protocol.message.messages;

public enum D2MessageType {
    PROTOCOL_REQUIRED(1), 
    UNKNOWN(-1);

    private int id;

    D2MessageType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
