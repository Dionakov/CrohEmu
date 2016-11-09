package org.crohemu.config;

import org.crohemu.network.d2protocol.D2MessageHandler;
import org.crohemu.network.d2protocol.message.D2MessageFactory;
import org.crohemu.network.d2protocol.message.impl.D2MessageFactoryImpl;
import org.crohemu.server.auth.D2AuthServerMessageHandler;
import org.springframework.context.annotation.Bean;

/**
 * Spring Bean configuration.
 *
 * @author Croh
 */
public class BeanConfig {

    @Bean(name = "d2AuthServerMessageHandler")
    D2MessageHandler d2AuthServerMessageHandler() {
        return new D2AuthServerMessageHandler();
    }

    @Bean
    D2MessageFactory d2MessageFactory() {
        return new D2MessageFactoryImpl();
    }
}
