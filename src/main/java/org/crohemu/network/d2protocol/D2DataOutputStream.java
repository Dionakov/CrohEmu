package org.crohemu.network.d2protocol;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class D2DataOutputStream extends DataOutputStream {

    private static final int BYTE_NEGATIVE_LIMIT = 0b10000000;
    private static final int CHUNK_BIT_SIZE = 7;
    private static final int LOWEST_BYTE_MASK = 0b11111111;

    private ByteArrayOutputStream underlyingStream;

    public D2DataOutputStream() {
        this(new ByteArrayOutputStream());
    }

    private D2DataOutputStream(OutputStream out) {
        super(out);
        this.underlyingStream = (ByteArrayOutputStream) out;
    }

    public void writeVariableLengthInt(int i) throws IOException {
        while (i != 0) {
            int lastByte = (i & LOWEST_BYTE_MASK);

            i = i >>> CHUNK_BIT_SIZE;

            this.write(i > 0
                    ? (byte) (lastByte | BYTE_NEGATIVE_LIMIT)
                    : (byte) lastByte);
        }
    }

    public ByteArrayOutputStream getUnderlyingStream() {
        return underlyingStream;
    }
}
