package com.abhishekvermaa10.config;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.abhishekvermaa10.tools.CustomTools;

/**
 * @author abhishekvermaa10
 */
@Configuration
public class McpServerConfig {
	
	@Bean
	ToolCallbackProvider toolCallbackProvider(CustomTools customTools) {
		return MethodToolCallbackProvider.builder()
				.toolObjects(customTools)
				.build();
	}

}
