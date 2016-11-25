package org.crohemu.server.auth;

import org.crohemu.network.d2protocol.message.messages.login.HelloConnectMessage;
import org.crohemu.network.d2protocol.message.messages.login.ProtocolRequiredMessage;
import org.crohemu.network.tcpwrapper.handler.TcpClientConnectionHandler;
import org.crohemu.network.tcpwrapper.handler.TcpDataHandler;
import org.crohemu.network.tcpwrapper.impl.TcpServerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;

@Component
public class AuthServer extends TcpServerImpl {
    Logger logger = LoggerFactory.getLogger(AuthServer.class);

    @Resource(name = "authServerDataHandler")
    TcpDataHandler authServerDataHandler;

    private TcpClientConnectionHandler clientConnectedHandler = client -> {
        if (AuthServerConfig.execModeLessThan(AuthServerConfig.ExecMode.RELEASE)) {
            logger.info("Client [{}] connected to auth server", client.getSocket().getInetAddress());
        }

        try {
            client.send(new ProtocolRequiredMessage(AuthServerConfig.getProtocolVersion(),
                    AuthServerConfig.getProtocolVersion()));

            HelloConnectMessage helloConnectMessage = new HelloConnectMessage();
            helloConnectMessage.setSalt("abcdef");
            helloConnectMessage.setKey(new byte[] {5, 4, 3, 2, 1});

            client.send(helloConnectMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };


    public void start() throws IOException {
        InetAddress serverAddress = InetAddress.getByName(AuthServerConfig.getIpAddress());
        int serverPortNumber = AuthServerConfig.getPortNumber();
        setDataHandler(authServerDataHandler);
        setClientConnectionHandler(clientConnectedHandler);
        super.start(serverAddress, serverPortNumber);
    }
}
