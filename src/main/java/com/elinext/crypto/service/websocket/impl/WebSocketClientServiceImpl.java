package com.elinext.crypto.service.websocket.impl;

import com.elinext.crypto.configuration.ApplicationProperties;
import com.elinext.crypto.service.websocket.WebSocketClientService;
import com.elinext.crypto.utils.WebSocketUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebSocketClientServiceImpl implements WebSocketClientService {

	private final WebSocketSessionHandler sessionHandler;
	private final WebSocketClient webSocketClient;
	private final ApplicationProperties applicationProperties;
	private final ObjectMapper objectMapper;

	@Override
	@SneakyThrows
	public void connect() {

		ApplicationProperties.WebSocket webSocket = applicationProperties.getWebSocket();
		String apiKey = webSocket.getApiKey();
		String apiSecret = webSocket.getApiSecret();
		String connectionUrl = webSocket.getConnectionUrl();
		WebSocketHttpHeaders headers = WebSocketUtils.subscribeHeaders(apiKey, apiSecret);
		var future = webSocketClient.doHandshake(sessionHandler, headers, URI.create(connectionUrl));
		future.addCallback(session -> {
			try {
				Objects.requireNonNull(session).sendMessage(WebSocketUtils.subscribeMessage(objectMapper));
			} catch (IOException e) {
				log.info("Exceptionally", e);
			}
		}, exceptionally -> log.info("Exceptionally", exceptionally));
	}
}
