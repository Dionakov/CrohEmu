package org.crohemu.network.d2protocol.message.messages.login;

import org.crohemu.network.tcpwrapper.TcpClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class HelloConnectMessageTest {

    public static final String SALT = "my_awesome_salt";
    public static final byte[] KEY = new byte[]{0, 1, 2, 3, 4};

    @Test
    public void serializeDeserializeTest() throws Exception {
        // Arrange
        HelloConnectMessage messageOut = new HelloConnectMessage();
        messageOut.setSalt(SALT);
        messageOut.setKey(KEY);
        messageOut.serialize();

        // Act
        HelloConnectMessage messageIn = new HelloConnectMessage(messageOut.getRawContent(), new TcpClient(null));

        // Assert
        Assert.assertEquals(SALT, messageIn.getSalt());
        Assert.assertArrayEquals(KEY, messageIn.getKey());
    }

}