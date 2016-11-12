package org.crohemu.server.common.controller;

import org.crohemu.network.d2protocol.message.messages.D2MessageType;

import java.util.HashMap;

public abstract class MessageControllerMap extends HashMap<D2MessageType, MessageController> {

}
