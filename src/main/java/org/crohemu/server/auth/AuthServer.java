package org.crohemu.server.auth;

import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Authentication server. Handles login, server selection, etc.
 *
 * @author Croh
 */
@Component
public interface AuthServer {

    public void listen() throws IOException;
}
