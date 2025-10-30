package com.abhishekvermaa10.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@RequestMapping("/ai")
@RestController
public class ChatController {
	
	private final ChatClient chatClient;
	private final ToolCallbackProvider toolCallbackProvider;
	
	@GetMapping("/generate")
	public ResponseEntity<String> generate(@RequestParam String userPrompt) {
		String response = chatClient.prompt()
				.user(userPrompt)
				.call()
				.content();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/generateWithMcp")
	public ResponseEntity<String> generateWithMcp(@RequestParam String userPrompt) {
		String response = chatClient.prompt()
				.toolCallbacks(toolCallbackProvider)
				.user(userPrompt)
				.call()
				.content();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
