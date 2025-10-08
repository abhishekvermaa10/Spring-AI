package com.abhishekvermaa10.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author abhishekvermaa10
 */
@Slf4j
@Component
public class LoggingAdvisor implements CallAdvisor  {
	
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
		logRequest(chatClientRequest);
		ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
		logResponse(chatClientResponse);
		return chatClientResponse;
	}
	
	private void logRequest(ChatClientRequest chatClientRequest) {
		log.info("Request {}", chatClientRequest);
	}
	
	private void logResponse(ChatClientResponse chatClientResponse) {
		log.info("Response {}", chatClientResponse);
	}

}
