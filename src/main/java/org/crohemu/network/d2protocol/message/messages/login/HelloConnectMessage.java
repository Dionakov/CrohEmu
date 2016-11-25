package org.crohemu.network.d2protocol.message.messages.login;

import org.crohemu.network.d2protocol.D2DataInputStream;
import org.crohemu.network.d2protocol.D2DataOutputStream;
import org.crohemu.network.d2protocol.message.D2Message;
import org.crohemu.network.d2protocol.message.messages.D2MessageType;
import org.crohemu.network.tcpwrapper.TcpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class HelloConnectMessage extends D2Message {

    private String salt;
    private byte[] key;

    public HelloConnectMessage(byte[] messageData, TcpClient source) {
        super(messageData, source);
        this.type = D2MessageType.HELLO_CONNECT;
    }

    public HelloConnectMessage() {
        super();
        this.type = D2MessageType.HELLO_CONNECT;
        this.salt = "";
        this.key = new byte[0];
    }

    @Override
    protected void deserialize(byte[] messageData, int contentOffset) {
        D2DataInputStream inputStream = new D2DataInputStream(messageData, contentOffset, messageData.length);

        try {
            this.salt = inputStream.readUTF();
            int keyLength = inputStream.readVariableLengthInt();

            this.key = new byte[keyLength];
            int bytesRead = inputStream.read(key);
            if (bytesRead != keyLength) {
                throw new IOException("Error while reading HelloConnect key : bad number of bytes read : " + bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void serialize() {

        D2DataOutputStream outputStream = new D2DataOutputStream();
        try {
            outputStream.writeUTF(salt);
            outputStream.writeVariableLengthInt(key.length);
            outputStream.write(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream underlyingOutputStream = outputStream.getUnderlyingStream();
        this.length = underlyingOutputStream.size();
        this.lengthType = computeLengthType(this.length);
        ByteBuffer byteBuffer = serializeHeader();
        byteBuffer.put(underlyingOutputStream.toByteArray());
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }
}
