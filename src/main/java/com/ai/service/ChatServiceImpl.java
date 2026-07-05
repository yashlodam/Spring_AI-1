package com.ai.service;

import java.util.Map;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;



@Service
public class ChatServiceImpl implements ChatService {
	
	
	private ChatClient chatClient;
	
	public ChatServiceImpl(ChatClient.Builder builder) {
		this.chatClient = builder.build();
	}
	
	@Value("classpath:/prompts/user-message.st")
	private Resource userMessage; 
	
	@Value("classpath:/prompts/system-message.st")
	private Resource systemMessage;
	

	@Override
	public String chat(String query) {
		
		
		
		// call the llm for response ;
		
//	String content = chatClient.prompt().user(prompt).system("As an expert in criceket").call().content();
		
		Prompt prompt1 = new Prompt(query);
		//modify this prompt and extra things to prompt make it more interactive;
		
		String queryStr = "As an expert in coding and programming. Always write program in java . Now reply for this question : {query}";
		
		String t = chatClient
				.prompt()
				.user(u-> u.text(queryStr).param("query", query))
				.call()
				.content();
	
		
	return t;
		
	}


	@Override
	public String chatTemplate(String q) {

	    PromptTemplate promptTemplate = PromptTemplate.builder()
	            .template("What is {techName}? Tell me an example of {exampleName}.")
	            .build();

	    String techName = (q == null || q.isBlank()) ? "Java" : q;
	    String exampleName = (q == null || q.isBlank()) ? "Object-Oriented Programming" : q;

	    String prompt = promptTemplate.render(Map.of(
	            "techName", techName,
	            "exampleName", exampleName
	    ));

	    return chatClient.prompt(prompt)
	            .system("""
	                    You are an expert Java Full Stack Developer and AI tutor.

	                    Follow these rules:
	                    - Explain concepts in simple and easy-to-understand language.
	                    - Start with a clear definition.
	                    - Explain why the technology is used.
	                    - Give one real-world example.
	                    - Provide a simple code example whenever applicable.
	                    - Mention key features or advantages.
	                    - End with a short summary.
	                    - Keep the response beginner-friendly.
	                    """)
	            .call()
	            .content();
	}	
	
	
	
	@Override
	public String generateProductDescription(String productName) {

	    PromptTemplate promptTemplate = PromptTemplate.builder()
	            .template("""
	                    Generate a professional product description for {productName}.
	                    """)
	            .build();

	    String product = (productName == null || productName.isBlank())
	            ? "Men's Cotton T-Shirt"
	            : productName;

	    String prompt = promptTemplate.render(Map.of(
	            "productName", product
	    ));

	    return chatClient.prompt(prompt)
	            .system("""
	                    You are an expert e-commerce copywriter.

	                    Your job is to generate high-quality product descriptions for an online shopping website.

	                    Follow these rules:
	                    - Write in a professional and engaging tone.
	                    - Start with a short introduction.
	                    - Highlight key features.
	                    - Mention benefits instead of only features.
	                    - Use persuasive language.
	                    - Keep the description between 100–150 words.
	                    - Make it SEO-friendly.
	                    - Do not include false claims.
	                    - End with a call-to-action encouraging customers to buy.
	                    """)
	            .call()
	            .content();
	}


	@Override
	public String systemChat(String query) {

	    var systemPromptTemplate = SystemPromptTemplate.builder()
	            .template("""
	                    You are a helpful coding assistant.

	                    Rules:
	                    - You are an expert in Java, Spring Boot, Spring AI, React, SQL, and REST APIs.
	                    - Explain concepts in simple language.
	                    - Give step-by-step explanations.
	                    - Provide clean and production-ready code examples.
	                    - Mention best practices.
	                    - Keep answers beginner-friendly.
	                    """)
	            .build();

	    var systemMessage = systemPromptTemplate.createMessage();

	    var userPromptTemplate = PromptTemplate.builder()
	            .template("""
	                    Answer the following question:

	                    {query}
	                    """)
	            .build();

	    var userMessage = userPromptTemplate.createMessage(
	            Map.of("query", query)
	    );

	    Prompt prompt = new Prompt(systemMessage, userMessage);

	    return chatClient.prompt(prompt)
	            .call()
	            .content();
	}


	@Override
	public String fluentChatAPI(String q) {

	    return chatClient.prompt()
	            .system(system -> system.text(systemMessage))
	            .user(user -> user.text(userMessage).param("concept", "spring validation"))
	            .call()
	            .content();
	}


	

	
	
}
