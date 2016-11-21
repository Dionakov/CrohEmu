package org.crohemu.network.d2protocol.message.messages;

public enum D2MessageType {
    UNKNOWN(-1),
    RAW(-209332),
    PROTOCOL_REQUIRED(1);

    private int id;

    D2MessageType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
