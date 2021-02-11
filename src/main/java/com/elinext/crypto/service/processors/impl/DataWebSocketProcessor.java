package com.elinext.crypto.service.processors.impl;

import com.elinext.crypto.dto.WebSocketChannelDto;
import com.elinext.crypto.dto.WebSocketChannelType;
import com.elinext.crypto.service.processors.WebSocketProcessor;
import com.elinext.crypto.service.processors.WebSocketProcessorQualifier;
import com.elinext.crypto.service.provider.ViewDataHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Component
@WebSocketProcessorQualifier(WebSocketChannelType.DATA)
@RequiredArgsConstructor
@Slf4j
public class DataWebSocketProcessor implements WebSocketProcessor {

	private final ViewDataHandler dataHandler;

	@Override
	public void process(WebSocketChannelDto channelDto) {

		CompletableFuture.runAsync(() -> {
			var data = channelDto.getData();
			data.stream().filter(obj -> obj.getSize().compareTo(BigDecimal.ZERO) > 0).forEach(dataHandler::handle);
		}).exceptionally(exceptionally -> {
			log.error("Exceptionally", exceptionally);
			return null;
		});
	}
}
