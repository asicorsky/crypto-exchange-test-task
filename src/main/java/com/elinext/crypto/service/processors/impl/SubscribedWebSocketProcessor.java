package com.elinext.crypto.service.processors.impl;

import com.elinext.crypto.dto.WebSocketChannelDto;
import com.elinext.crypto.dto.WebSocketChannelType;
import com.elinext.crypto.service.processors.WebSocketProcessor;
import com.elinext.crypto.service.processors.WebSocketProcessorQualifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@WebSocketProcessorQualifier(WebSocketChannelType.SUBSCRIBED)
@Slf4j
public class SubscribedWebSocketProcessor implements WebSocketProcessor {

	@Override
	public void process(WebSocketChannelDto channelDto) {

		log.info("Application successfully subscribed to required websocket channel...");
	}
}
