package com.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ai.advisors.TokenPrintAdvisor;

@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {

        return builder
                .defaultAdvisors(new TokenPrintAdvisor())
                .defaultSystem("""
                        You are a helpful coding assistant.
                        You are an expert in coding.
                        """)
                .defaultOptions(
                        OpenAiChatOptions.builder()
                                
                )
                .build();
    }
}