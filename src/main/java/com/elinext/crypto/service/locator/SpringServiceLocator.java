package com.elinext.crypto.service.locator;

import com.elinext.crypto.dto.WebSocketChannelType;
import com.elinext.crypto.service.processors.WebSocketProcessor;

public interface SpringServiceLocator {

	WebSocketProcessor webSocketProcessor(WebSocketChannelType type);
}
