package com.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.service.ChatService;

@RestController
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	
	
	@GetMapping("/chat")
	public ResponseEntity<String> chat(@RequestParam(required = false) String q){
		
		String response = chatService.fluentChatAPI(q);
		
		return ResponseEntity.ok(response);
	}
}
