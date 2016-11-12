package org.crohemu.config;

import org.crohemu.network.d2protocol.D2TcpDataHandler;
import org.crohemu.network.d2protocol.message.D2MessageFactory;
import org.crohemu.network.d2protocol.message.impl.D2MessageFactoryImpl;
import org.crohemu.network.tcpwrapper.TcpDataHandler;
import org.crohemu.server.auth.AuthServer;
import org.crohemu.server.auth.AuthServerMessageHandler;
import org.crohemu.server.auth.controller.AuthMessageControllerMap;
import org.crohemu.server.common.controller.MessageControllerMap;
import org.springframework.context.annotation.Bean;

/**
 * Spring Bean configuration.
 *
 * @author Croh
 */
public class BeanConfig {

    @Bean(name = "authServerMessageControllerMap")
    MessageControllerMap authServerMessageControllerMap() {
        return new AuthMessageControllerMap();
    }

    @Bean
    AuthServerMessageHandler authServerMessageHandler() {
        return new AuthServerMessageHandler();
    }

    @Bean(name = "authServerDataHandler")
    TcpDataHandler authServerDataHandler() {
        return new D2TcpDataHandler(authServerMessageHandler());
    }

    @Bean
    D2MessageFactory d2MessageFactory() {
        return new D2MessageFactoryImpl();
    }

    @Bean
    AuthServer authServer() {
        return new AuthServer();
    }
}
