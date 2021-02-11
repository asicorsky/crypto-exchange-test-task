package com.elinext.crypto.service.websocket;

import com.elinext.crypto.dto.WebSocketViewDto;

public interface WebSocketServerService {

	void appeared(WebSocketViewDto view);

	void removed(String uid);
}
