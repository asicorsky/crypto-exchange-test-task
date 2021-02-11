package com.elinext.crypto.configuration;

import com.elinext.crypto.service.rest.RestClientService;
import com.elinext.crypto.service.websocket.WebSocketClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
public class ApplicationListener {

	private final WebSocketClientService webSocketClientService;
	private final RestClientService restClientService;

	@EventListener(ApplicationReadyEvent.class)
	public void onReady() {

		webSocketClientService.connect();
		restClientService.connect();
	}
}
