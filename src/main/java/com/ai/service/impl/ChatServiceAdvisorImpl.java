package com.ai.service.impl;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ai.advisors.TokenPrintAdvisor;
import com.ai.service.ChatServiceAdvisor;

@Service
public class ChatServiceAdvisorImpl implements ChatServiceAdvisor {

    private final ChatClient chatClient;

    @Value("classpath:/prompts/user-message.st")
    private Resource userMessage;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemMessage;

    public ChatServiceAdvisorImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String chatTemplates(String query, String userId) {

        return this.chatClient
                .prompt()
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        userId
                ))
                .user(user -> user.text(this.userMessage)
                        .param("concept", query))
                .call()
                .content();
    }
}