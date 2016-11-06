package org.crohemu.server.auth;

import org.springframework.stereotype.Component;

import java.net.Socket;

/**
 * Manages the lifecycle and workflow of client sockets.
 */
@Component
public interface AuthClientHandler {

    public void addClient(Socket clientSocket);
}
