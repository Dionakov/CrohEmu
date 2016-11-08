package org.crohemu.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SocketInputStream extends BufferedInputStream {

    public SocketInputStream(InputStream in) {
        super(in);
    }

    public byte[] readAll() throws IOException {
        byte[] resultBuffer = new byte[0];
        byte[] buffer = new byte[1024];

        int bytesRead;
        while((bytesRead = read(buffer)) > -1) {
            byte[] tempBuffer = new byte[resultBuffer.length + bytesRead];
            System.arraycopy(resultBuffer,0, tempBuffer, 0, resultBuffer.length);
            System.arraycopy(buffer, 0, tempBuffer, resultBuffer.length, bytesRead);
            resultBuffer = tempBuffer;
        }

        return resultBuffer;
    }
}
