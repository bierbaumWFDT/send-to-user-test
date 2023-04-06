package com.widgetfinancial.userwebsocket.handshake;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.widgetfinancial.userwebsocket.principal.StompPrincipal;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
	private static final Logger log = LogManager.getLogger(CustomHandshakeHandler.class);
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
		String username = UUID.randomUUID().toString();
		log.info("Generated username {}", username);
		
		return new StompPrincipal(username);
	}
}
