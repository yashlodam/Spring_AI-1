package com.ai.service;


public interface ChatService {

	String chat(String query);
	String chatTemplate(String q);
	String generateProductDescription(String productName);
	String systemChat(String query);
	String fluentChatAPI(String q);
	
	
}
