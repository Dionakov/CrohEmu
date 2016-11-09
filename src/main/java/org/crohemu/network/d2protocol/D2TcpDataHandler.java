package org.crohemu.network.d2protocol;

import org.crohemu.network.d2protocol.message.D2MessageFactory;
import org.crohemu.network.tcpwrapper.TcpDataHandler;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

public class D2TcpDataHandler implements TcpDataHandler {
    private D2MessageHandler messageHandler;

    @Inject
    D2MessageFactory d2MessageFactory;

    public D2TcpDataHandler(D2MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void handleTcpData(InputStream socketIn) throws IOException {
        int messageLength = 0;
        do {
            byte[] messageData = readD2Message(socketIn);

            messageHandler.handleD2Message(d2MessageFactory.createMessage(messageData));

            messageLength = messageData.length;
        } while (messageLength > 0);
    }

    private byte[] readD2Message(InputStream socketIn) throws IOException {

        byte[] fullMessageBuffer = new byte[0];
        int totalBytesRead = 0;
        boolean fullMessageHasBeenRead = false;
        int messageTypeLen = 0;
        int messageLength = -1;

        // what we read in an iteration. Its size fluctuates.
        byte[] messagePartBuffer = new byte[2];

        // next number of bytes to read
        int nextChunkSize = 0;

        while (!fullMessageHasBeenRead) {
            int bytesRead = socketIn.read(messagePartBuffer);
            if (bytesRead == -1) {
                break;
            }

            // data is D2 message id + messageTypeLen
            if (totalBytesRead == 0) {
                messageTypeLen = messagePartBuffer[1] & 0b00000011;
                if (messageTypeLen == 0) {
                    throw new IOException("bad socket data");
                }
                nextChunkSize = messageTypeLen;
            }
            // data is D2 message messageLength
            else if (totalBytesRead == 2) {
                byte[] t = messagePartBuffer; // for shorter lines
                switch (messageTypeLen) {
                    case 1:
                        messageLength = t[0];
                        break;
                    case 2:
                        messageLength = t[0] << 8 | t[1];
                        break;
                    case 3:
                        messageLength = t[0] << 16 | t[1] << 8 | t[0];
                }

                nextChunkSize = messageLength;
            }
            // else data is message content. We just add it to the result buffer.

            totalBytesRead += bytesRead;
            if (messageLength != -1 && totalBytesRead == messageLength + messageTypeLen + 2) {
                fullMessageHasBeenRead = true;
            }

            // add bytes read to full message
            byte[] newFullMessageBuffer = new byte[fullMessageBuffer.length + bytesRead];
            System.arraycopy(fullMessageBuffer, 0, newFullMessageBuffer, 0, fullMessageBuffer.length);
            System.arraycopy(messagePartBuffer, 0, newFullMessageBuffer, fullMessageBuffer.length, bytesRead);
            fullMessageBuffer = newFullMessageBuffer;

            messagePartBuffer = new byte[nextChunkSize];
        }

        return fullMessageBuffer;
    }
}
