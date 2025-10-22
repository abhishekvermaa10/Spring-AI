package com.abhishekvermaa10.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.abhishekvermaa10.advisor.LoggingAdvisor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author abhishekvermaa10
 */
@Slf4j
@Configuration
public class ChatConfig {

	@Bean
	ChatClient chatClient(ChatClient.Builder chatClientBuilder, LoggingAdvisor loggingAdvisor) {
		return chatClientBuilder
				.defaultAdvisors(loggingAdvisor)
				.build();
	}

}
