package org.crohemu.network.d2protocol;

import org.crohemu.network.d2protocol.message.D2MessageFactory;
import org.crohemu.network.tcpwrapper.TcpClient;
import org.crohemu.network.tcpwrapper.handler.TcpDataHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

@Component
public class D2TcpDataHandler implements TcpDataHandler {
    public static final int HIGH_HEADER_LENGTH = 2;

    @Resource
    private D2MessageHandler messageHandler;

    @Inject
    D2MessageFactory d2MessageFactory;

    public D2TcpDataHandler(D2MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void handleTcpData(TcpClient client) throws IOException {
        int messageLength;
        do {
            byte[] messageData = readD2Message(client.getSocket().getInputStream());

            // connection closed
            if (messageData.length == 0) {
                return;
            }

            messageHandler.handleD2Message(d2MessageFactory.createMessage(messageData, client));

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
        byte[] messagePartBuffer = new byte[HIGH_HEADER_LENGTH];

        // next number of bytes to read
        int nextChunkSize = 0;

        while (!fullMessageHasBeenRead) {
            int bytesRead = socketIn.read(messagePartBuffer);
            if (bytesRead == -1) {
                break;
            }

            // data is D2 high header
            if (totalBytesRead == 0) {
                messageTypeLen = messagePartBuffer[1] & 0b00000011;
                if (messageTypeLen == 0) {
                    throw new IOException("bad socket data");
                }
                nextChunkSize = messageTypeLen;
            }
            // data is D2 messageLength
            else if (totalBytesRead == HIGH_HEADER_LENGTH) {
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
            if (messageLength != -1 && totalBytesRead == messageLength + messageTypeLen + HIGH_HEADER_LENGTH) {
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
