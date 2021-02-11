package com.elinext.crypto.service.processors.impl;

import com.elinext.crypto.dto.WebSocketChannelDto;
import com.elinext.crypto.dto.WebSocketChannelType;
import com.elinext.crypto.service.processors.WebSocketProcessor;
import com.elinext.crypto.service.processors.WebSocketProcessorQualifier;
import com.elinext.crypto.service.websocket.WebSocketClientService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@WebSocketProcessorQualifier(WebSocketChannelType.OFFLINE)
@Slf4j
@RequiredArgsConstructor
public class OfflineWebSocketProcessor implements WebSocketProcessor {

	private final WebSocketClientService webSocketClientService;

	@Override
	@SneakyThrows
	public void process(WebSocketChannelDto channelDto) {

		log.warn("Required server is offline. System will wait 1 minute and try to connect again...");
		TimeUnit.MINUTES.sleep(1);
		webSocketClientService.connect();
	}
}
