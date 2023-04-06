package com.widgetfinancial.userwebsocket.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.widgetfinancial.userwebsocket.domain.Message;
import com.widgetfinancial.userwebsocket.domain.OutputMessage;

@Controller
public class WebSocketController {
	private static final Logger logger = LogManager.getLogger(WebSocketController.class);
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/room") 
	public void sendSpecific(
	  @Payload Message msg, 
	  Principal user, 
	  @Header("simpSessionId") String sessionId) { 
	    OutputMessage out = new OutputMessage(
	      msg.getFrom(), 
	      msg.getText(),
	      new SimpleDateFormat("HH:mm").format(new Date())); 
	    simpMessagingTemplate.convertAndSendToUser(
	      msg.getTo(), "/user/queue/specific-user", out); 
	}
}
