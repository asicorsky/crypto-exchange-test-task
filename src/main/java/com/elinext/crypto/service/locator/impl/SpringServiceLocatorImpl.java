package com.elinext.crypto.service.locator.impl;

import com.elinext.crypto.dto.WebSocketChannelType;
import com.elinext.crypto.service.locator.SpringServiceLocator;
import com.elinext.crypto.service.processors.WebSocketProcessor;
import com.elinext.crypto.service.processors.WebSocketProcessorQualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SpringServiceLocatorImpl implements SpringServiceLocator {

	private final Map<WebSocketChannelType, WebSocketProcessor> processors = new ConcurrentHashMap<>();

	@Override
	public WebSocketProcessor webSocketProcessor(WebSocketChannelType type) {

		return processors.get(type);
	}

	@EventListener
	public void handleContextRefreshEvent(ContextRefreshedEvent event) {

		ApplicationContext applicationContext = event.getApplicationContext();
		var processorBeans = applicationContext.getBeansWithAnnotation(WebSocketProcessorQualifier.class);
		processorBeans.forEach((key, value) -> {
			WebSocketProcessorQualifier processorQualifier = applicationContext.findAnnotationOnBean(key, WebSocketProcessorQualifier.class);
			if (Objects.nonNull(processorQualifier)) {
				WebSocketChannelType type = processorQualifier.value();
				processors.putIfAbsent(type, (WebSocketProcessor) value);
			}
		});
	}
}
