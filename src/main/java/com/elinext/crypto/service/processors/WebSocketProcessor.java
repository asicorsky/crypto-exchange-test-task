package com.elinext.crypto.service.processors;

import com.elinext.crypto.dto.WebSocketChannelDto;

public interface WebSocketProcessor {

	void process(WebSocketChannelDto channelDto);
}
