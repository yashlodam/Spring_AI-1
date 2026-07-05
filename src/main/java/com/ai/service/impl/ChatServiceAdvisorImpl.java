package com.ai.service.impl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.ai.service.ChatServiceAdvisor;

public class ChatServiceAdvisorImpl implements ChatServiceAdvisor{
	
	
	private ChatClient chatClient;
	
	@Value("classpath:/prompts/user-message.st")
	private Resource userMessage; 
	
	@Value("classpath:/prompts/system-message.st")
	private Resource systemMessage;
	
	public ChatServiceAdvisorImpl(ChatClient.Builder builder) {
		this.chatClient = builder.build();
	}

	@Override
	public String chatTemplate(String query) {
	    
		return this.chatClient
				.prompt()
				.system(systemMessage)
				.user(user-> user.text(this.userMessage).param("concept", "java programming"))
				.call()
				.content()
				;
	}

}
