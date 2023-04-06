package com.widgetfinancial.userwebsocket.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.widgetfinancial.userwebsocket.domain.Message;
import com.widgetfinancial.userwebsocket.domain.OutputMessage;

@RestController
public class SendMessageController {
	private static final Logger logger = LogManager.getLogger(SendMessageController.class);
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	private SimpUserRegistry simpUserRegistry;
	
	@PostMapping(value = "/send/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void sendMessage(@PathVariable String username, @RequestBody Message message) {
		OutputMessage out = new OutputMessage(
				message.getFrom(), 
				message.getText(),
			      new SimpleDateFormat("HH:mm").format(new Date())); 
			    logger.info("Attempting to send a message with content {} to user {}", message.getText(), username);
			    simpMessagingTemplate.convertAndSendToUser(
			      username, "/user/queue/specific-user", out); 
	}
	
	@GetMapping(value="/info")
	public ResponseEntity<String> getSessionInfo(){
		StringBuilder report = new StringBuilder();
		
		Set<SimpUser> users = simpUserRegistry.getUsers();
		
		for(SimpUser s : users) {
			report.append(s.getName());
			report.append(System.getProperty("line.separator"));
		}
		
		return ResponseEntity.ok().body("There are currently " + simpUserRegistry.getUserCount() + " active sessions.\n"+report.toString());
	}
}
