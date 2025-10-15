package com.abhishekvermaa10.tools;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.util.json.schema.JsonSchemaGenerator;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * @author abhishekvermaa10
 */
@Component
public class CurrentDateTimeTool implements ToolCallback {

	@Override
	public ToolDefinition getToolDefinition() {
		Method method = ReflectionUtils.findMethod(CurrentDateTimeTool.class, "getCurrentDateTime");
		return ToolDefinition.builder()
			    .name("getCurrentDateTime")
			    .description("Get the current date and time in the user's timezone.")
			    .inputSchema(JsonSchemaGenerator.generateForMethodInput(method))
			    .build();
	}

	@Override
	public String call(String toolInput) {
		return getCurrentDateTime();
	}
	
	private String getCurrentDateTime() {
		return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
	}

}
