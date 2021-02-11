package com.elinext.crypto.service.websocket.impl;

import com.elinext.crypto.dto.WebSocketChannelDto;
import com.elinext.crypto.dto.WebSocketChannelType;
import com.elinext.crypto.service.locator.SpringServiceLocator;
import com.elinext.crypto.service.processors.WebSocketProcessor;
import com.elinext.crypto.utils.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketSessionHandler extends AbstractWebSocketHandler {

	private final ObjectMapper objectMapper;
	private final SpringServiceLocator serviceLocator;

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		WebSocketChannelDto channel = HttpUtils.asWebSocketChannel(message, objectMapper);
		WebSocketChannelType type = channel.getType();
		WebSocketProcessor processor = serviceLocator.webSocketProcessor(type);
		processor.process(channel);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

		log.error("Cannot connect to web socket session", exception);
	}
}
