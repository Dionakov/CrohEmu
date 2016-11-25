package org.crohemu.network.d2protocol;

import java.io.*;

public class D2DataInputStream extends DataInputStream {

    private static final int BYTE_NEGATIVE_LIMIT = 0b10000000;
    private static final int BYTE_POSITIVE_LIMIT = 0b01111111;
    private static final int CHUNK_BIT_SIZE = 7;

    private ByteArrayInputStream underlyingStream;

    public D2DataInputStream(byte[] buffer) {
        this(new ByteArrayInputStream(buffer));
    }

    public D2DataInputStream(byte[] buffer, int offset, int length) {
        this(new ByteArrayInputStream(buffer, offset, length));
    }

    private D2DataInputStream(InputStream in) {
        super(in);
        this.underlyingStream = (ByteArrayInputStream) in;
    }

    public int readVariableLengthInt() throws IOException {

        int result = 0;
        int bitsRead = 0;
        while (bitsRead < Integer.SIZE) {
            int latestByteRead = readByte();
            result = result + ((latestByteRead & BYTE_POSITIVE_LIMIT) << bitsRead);
            bitsRead = bitsRead + CHUNK_BIT_SIZE;

            if ((latestByteRead & BYTE_NEGATIVE_LIMIT) != BYTE_NEGATIVE_LIMIT) {
                return result;
            }
        }
        throw new IOException("Too much data");
    }

    public ByteArrayInputStream getUnderlyingStream() {
        return underlyingStream;
    }
}
