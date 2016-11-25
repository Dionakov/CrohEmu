package org.crohemu.network.d2protocol;

import org.junit.Assert;
import org.junit.Test;

public class D2DataStreamTest {
    @Test
    public void writeReadVariableLengthInt() throws Exception {
        D2DataOutputStream out = new D2DataOutputStream();
        out.writeVariableLengthInt(3000);

        D2DataInputStream in = new D2DataInputStream(out.getUnderlyingStream().toByteArray());
        Assert.assertEquals(3000, in.readVariableLengthInt());
    }
}