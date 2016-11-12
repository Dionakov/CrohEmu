package org.crohemu.network.d2protocol.message;

import org.crohemu.network.d2protocol.D2TcpDataHandler;
import org.crohemu.network.d2protocol.message.messages.D2MessageType;
import org.crohemu.network.tcpwrapper.TcpClient;
import org.crohemu.network.tcpwrapper.TcpMessage;

import java.nio.ByteBuffer;

public abstract class D2Message extends TcpMessage {


    public static final int HIGH_HEADER_SIZE = 2;
    public static final int SIZEOF_INT = 4;

    protected D2MessageType type;

    /**
     * The number of bytes needed to encode the message's length (1|2|3 bytes)
     */
    protected int lengthType;

    /**
     * the message's length in bytes.
     */
    protected int length;

    public D2Message(byte[] messageData, TcpClient source) {
        super(messageData, source);

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

        this.deserialize(messageData, D2TcpDataHandler.HIGH_HEADER_LENGTH + this.lengthType);
    }

    public D2Message() {
        super();
    }

    /**
     * Extract the message content data from the raw bytes.
     * @param messageData The raw socket bytes
     * @param contentOffset The offset at which the actual content starts
     */
    protected abstract void deserialize(byte[] messageData, int contentOffset);

    public abstract void serialize();

    protected ByteBuffer serializeHeader() {
        rawContent = new byte[length + lengthType + HIGH_HEADER_SIZE];

        ByteBuffer byteBuffer = ByteBuffer.wrap(rawContent);

        byte[] highHeader = new byte[2];
        highHeader[0] = (byte) (type.getId() >> 6);
        highHeader[1] = (byte) (type.getId() << 2 & lengthType);

        byteBuffer.put(highHeader);

        switch (lengthType) {
            case 1:
                byteBuffer.put((byte) length);
                break;
            case 2:
                byteBuffer.putShort((short) length);
                break;
            case 3:
                byteBuffer.put((byte) (length >> 16));
                byteBuffer.put((byte) (length >> 8));
                byteBuffer.put((byte) length);
                break;
        }

        return byteBuffer;
    }

    public D2MessageType getType() {
        return type;
    }

    public int getLengthType() {
        return lengthType;
    }

    public int getLength() {
        return length;
    }

    protected int computeLengthType(int length) {
        if (length < 256) {
            return 1;
        } else if (length < 36536) {
            return 2;
        } else {
            return 3;
        }
    }
}
