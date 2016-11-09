package org.crohemu.network.d2protocol.message;

public abstract class D2Message {

    private int id;

    /**
     * The number of bytes needed to encode the message's length (1|2|3 bytes)
     */
    private int lengthType;

    /**
     * the message's length in bytes.
     */
    private int length;

    public D2Message(byte[] messageData) {
        this.id = (messageData[0] << 6) | (messageData[1] >> 2);
        this.lengthType = messageData[1] & 0b00000011;
        switch (this.lengthType) {
            case 1:
                this.length = messageData[2];
                break;
            case 2:
                this.length = messageData[2] << 8 | messageData[3];
                break;
            case 3:
                this.length = messageData[2] << 16 | messageData[3] << 8 | messageData[4];
                break;
        }

        // todo call deserialize with the actual message data
    }

    abstract void deserialize(byte[] messageContent);
}
