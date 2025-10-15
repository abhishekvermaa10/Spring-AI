package com.abhishekvermaa10.tools;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.util.json.schema.JsonSchemaGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author abhishekvermaa10
 */
@Component
public class PrintTool implements ToolCallback {
	
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ToolDefinition getToolDefinition() {
		Method method = ReflectionUtils.findMethod(PrintTool.class, "print", String.class);
		return ToolDefinition.builder()
			    .name("print")
			    .description("Print the given message to the console.")
			    .inputSchema(JsonSchemaGenerator.generateForMethodInput(method))
			    .build();
	}

	@Override
	public String call(String toolInput) {
		Map<String, String> input;
		try {
			input = objectMapper.readValue(toolInput, new TypeReference<Map<String, String>>() {});
		} catch (JsonProcessingException e) {
			return "Something went wrong: " + e.getMessage();
		}
		String message = input.get("message");
		print(message);
		return "Printed Successfully";
	}
	
	private void print(String message) {
		System.out.println("Printed: " + message);		
	}

}
