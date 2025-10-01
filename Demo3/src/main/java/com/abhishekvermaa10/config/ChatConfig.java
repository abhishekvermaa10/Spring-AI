package com.abhishekvermaa10.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
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
			SimpleLoggerAdvisor simpleLoggerAdvisor) {
		return chatClientBuilder
				.defaultAdvisors(loggingAdvisor)
				.defaultAdvisors(simpleLoggerAdvisor)
				.build();
	}
	
	@Bean
	SimpleLoggerAdvisor simpleLoggerAdvisor() {
		return new SimpleLoggerAdvisor();
	}

}
