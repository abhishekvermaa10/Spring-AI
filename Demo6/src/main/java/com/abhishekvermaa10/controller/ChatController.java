package com.abhishekvermaa10.controller;

import java.io.IOException;
import java.util.Base64;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.stabilityai.StabilityAiImageModel;
import org.springframework.ai.stabilityai.StyleEnum;
import org.springframework.ai.stabilityai.api.StabilityAiImageOptions;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@RequestMapping("/ai")
@RestController
public class ChatController {
	
	private final ChatClient chatClient;
	private final OpenAiImageModel openaiImageModel;
	private final StabilityAiImageModel stabilityaiImageModel;
	
	@GetMapping("/generate")
	public ResponseEntity<String> generate(@RequestParam String userPrompt) {
		String response = chatClient.prompt()
				.user(userPrompt)
				.call()
				.content();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/generateDescription")
	public ResponseEntity<String> generateDescription(@RequestParam String userPrompt, @RequestParam MultipartFile file) throws IOException {
		Resource image = new ByteArrayResource(file.getBytes()) {
			@Override
			public String getFilename() {
				return file.getOriginalFilename();
			}
		};
		String response = chatClient.prompt()
				.user(u -> {
					u.text(userPrompt);
					u.media(MimeTypeUtils.IMAGE_JPEG, image);
				})
				.call()
				.content();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/generateOpenAiImage")
	public ResponseEntity<byte[]> generateOpenAiImage(@RequestParam String userPrompt) {
		ImageResponse imageResponse = openaiImageModel.call(
		        new ImagePrompt(userPrompt,
		        OpenAiImageOptions.builder()
		        		.N(1)
		                .quality("standard")
		                .responseFormat("b64_json")
		                .width(1024)
		                .height(1024)
		                .style("natural")
		                .build()));
		String b64Response = imageResponse.getResult().getOutput().getB64Json();
		byte[] response = Base64.getDecoder().decode(b64Response);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(response);
	}
	
	@GetMapping("/generateStabilityAiImage")
	public ResponseEntity<byte[]> generateStabilityAiImage(@RequestParam String userPrompt) {
		ImageResponse imageResponse = stabilityaiImageModel.call(
		        new ImagePrompt(userPrompt,
		        		StabilityAiImageOptions.builder()
		        		.N(1)
		        		.width(1024)
		                .height(1024)
		                .responseFormat("image/png")
		                .stylePreset(StyleEnum.COMIC_BOOK)
		                .build()));
		String b64Response = imageResponse.getResult().getOutput().getB64Json();
		byte[] response = Base64.getDecoder().decode(b64Response);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(response);
	}

}
