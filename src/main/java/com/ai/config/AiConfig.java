package com.ai.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ai.advisors.TokenPrintAdvisor;

@Configuration
public class AiConfig {
	
	@Bean
	public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcchatMemoryRespository) {
		
		return MessageWindowChatMemory.builder()
				.chatMemoryRepository(jdbcchatMemoryRespository)
				.maxMessages(10)
				.build();
	}
	

    private static final Logger logger =
            LoggerFactory.getLogger(AiConfig.class);

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder,
                                 ChatMemory chatMemory) {

        logger.info("Chat Memory Implementation : {}",
                chatMemory.getClass().getName());

        MessageChatMemoryAdvisor memoryAdvisor =
                MessageChatMemoryAdvisor.builder(chatMemory)
                        .build();

        return builder
                .defaultAdvisors(
                        memoryAdvisor
                )
                .defaultSystem("""
                        You are a helpful coding assistant.
                        You are an expert in Java, Spring Boot,
                        Spring AI, SQL, and React.
                        """)
                .defaultOptions(
                        OpenAiChatOptions.builder()
                                
                )
                .build();
    }
}