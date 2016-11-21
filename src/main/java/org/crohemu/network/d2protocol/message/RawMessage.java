package org.crohemu.network.d2protocol.message;

import org.crohemu.network.d2protocol.message.messages.D2MessageType;
import org.crohemu.network.tcpwrapper.TcpClient;

import java.nio.ByteBuffer;

public class RawMessage extends D2Message {

    private byte[] content;

    public RawMessage(byte[] messageData, TcpClient source) {
        type = D2MessageType.RAW;
        rawContent = messageData;
        this.source = source;
        // TODO deserialize
    }

    public RawMessage(byte[] content) {
        type = D2MessageType.RAW;
        this.content = content;
        this.length = content.length;
        this.serialize();
    }

    @Override
    protected void deserialize(byte[] messageData, int contentOffset) {
    }

    @Override
    public void serialize() {
        this.rawContent = new byte[content.length + SIZEOF_INT];
        ByteBuffer byteBuffer = ByteBuffer.wrap(rawContent);

        switch (computeLengthType(length)) {
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

        byteBuffer.put(content);
    }
}
