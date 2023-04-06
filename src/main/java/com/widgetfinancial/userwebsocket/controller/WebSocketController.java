package com.widgetfinancial.userwebsocket.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import com.widgetfinancial.userwebsocket.domain.Message;
import com.widgetfinancial.userwebsocket.domain.OutputMessage;

@Controller
public class WebSocketController {
	private static final Logger logger = LogManager.getLogger(WebSocketController.class);
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	private String username = "";
	
	@MessageMapping("/room") 
	public void sendSpecific(
	  @Payload Message msg, 
	  Principal user, 
	  @Header("simpSessionId") String sessionId) { 
	    OutputMessage out = new OutputMessage(
	      msg.getFrom(), 
	      msg.getText(),
	      new SimpleDateFormat("HH:mm").format(new Date())); 
	    logger.info("Attempting to send a message with content {} to user {}", msg.getText(), username);
	    simpMessagingTemplate.convertAndSendToUser(
	      username, "/user/queue/specific-user", out); 
	}
	
	@EventListener
	public void handleSessionConnectedEvent(SessionConnectedEvent event) {
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
		username = sha.getUser().getName();
		logger.info("Found connection with username {}", sha.getUser().getName());
	}
}