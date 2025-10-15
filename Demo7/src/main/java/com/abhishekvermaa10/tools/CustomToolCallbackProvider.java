package com.abhishekvermaa10.tools;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@Component
public class CustomToolCallbackProvider implements ToolCallbackProvider {
	
	private final CurrentDateTimeTool currentDateTimeTool;
	private final PrintTool printTool;

	@Override
	public ToolCallback[] getToolCallbacks() {
		return new ToolCallback[] {currentDateTimeTool, printTool};
	}

}
