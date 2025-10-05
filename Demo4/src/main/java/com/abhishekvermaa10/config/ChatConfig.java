package com.abhishekvermaa10.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.abhishekvermaa10.advisor.LoggingAdvisor;

/**
 * @author abhishekvermaa10
 */
@Configuration
public class ChatConfig {
	
	@Bean
	ChatClient chatClient(ChatClient.Builder chatClientBuilder, LoggingAdvisor loggingAdvisor,
			MessageChatMemoryAdvisor messageChatMemoryAdvisor) {
		return chatClientBuilder
				.defaultAdvisors(loggingAdvisor)
				.defaultAdvisors(messageChatMemoryAdvisor)
				.build();
	}
	
	@Bean
	MessageChatMemoryAdvisor messageChatMemoryAdvisor(ChatMemory chatMemory) {
		return MessageChatMemoryAdvisor.builder(chatMemory)
				.build();
	}
	
}
