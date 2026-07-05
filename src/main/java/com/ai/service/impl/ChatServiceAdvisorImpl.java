package com.ai.service.impl;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ai.service.ChatServiceAdvisor;

@Service
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
	public String chatTemplates(String query) {
	    
		return this.chatClient
				.prompt()
				.advisors(new SimpleLoggerAdvisor(),new SafeGuardAdvisor(List.of("games")))
				.system(systemMessage)
				.user(user-> user.text(this.userMessage).param("concept", query))
				.call()
				.content()
				;
	}

}
