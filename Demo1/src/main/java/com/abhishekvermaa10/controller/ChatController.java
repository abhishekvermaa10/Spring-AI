package com.abhishekvermaa10.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhishekvermaa10.dto.Countries;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@RequestMapping("/ai")
@RestController
public class ChatController {
	
	private final ChatClient chatClient;
	
	@GetMapping("/generate")
	public ResponseEntity<String> generate(@RequestParam String userPrompt) {
		String response = chatClient.prompt()
				.user(userPrompt)
				.call()
				.content();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/generateStream")
	public ResponseEntity<Flux<String>> generateStream(@RequestParam String userPrompt) {
		Flux<String> response = chatClient.prompt()
				.user(userPrompt)
				.stream()
				.content();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/generateChatResponse")
	public ResponseEntity<ChatResponse> generateChatResponse(@RequestParam String userPrompt) {
		ChatResponse response = chatClient.prompt()
				.user(userPrompt)
				.call()
				.chatResponse();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/generateJson")
	public ResponseEntity<Countries> generateJson(@RequestParam String userPrompt) {
		Countries response = chatClient.prompt()
				.user(userPrompt)
				.call()
				.entity(Countries.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
