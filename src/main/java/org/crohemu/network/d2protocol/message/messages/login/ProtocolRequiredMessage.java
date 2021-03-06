package org.crohemu.network.d2protocol.message.messages.login;

import org.crohemu.network.d2protocol.message.D2Message;
import org.crohemu.network.d2protocol.message.messages.D2MessageType;
import org.crohemu.network.tcpwrapper.TcpClient;

import java.nio.ByteBuffer;

public final class ProtocolRequiredMessage extends D2Message {

    private int requiredVersion;
    private int currentVersion;

    public ProtocolRequiredMessage(byte[] messageData, TcpClient source) {
        super(messageData, source);
        type = D2MessageType.PROTOCOL_REQUIRED;
    }

    public ProtocolRequiredMessage(int requiredVersion, int currentVersion) {
        super();
        type = D2MessageType.PROTOCOL_REQUIRED;
        this.requiredVersion = requiredVersion;
        this.currentVersion = currentVersion;
    }

    @Override
    protected void deserialize(byte[] messageData, int contentOffset) {
        ByteBuffer messageDataByteBuffer = ByteBuffer.wrap(messageData, contentOffset, messageData.length - contentOffset);

        this.requiredVersion = messageDataByteBuffer.getInt();
        this.currentVersion = messageDataByteBuffer.getInt();
    }

    @Override
    public void serialize() {
        this.length = 2 * Integer.BYTES;
        this.lengthType = this.computeLengthType(this.length);
        ByteBuffer byteBuffer = serializeHeader();

        byteBuffer.putInt(requiredVersion);
        byteBuffer.putInt(currentVersion);
    }

    public int getRequiredVersion() {
        return requiredVersion;
    }

    public int getCurrentVersion() {
        return currentVersion;
    }

    public void setRequiredVersion(int requiredVersion) {
        this.requiredVersion = requiredVersion;
    }

    public void setCurrentVersion(int currentVersion) {
        this.currentVersion = currentVersion;
    }
}