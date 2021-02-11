package com.elinext.crypto.service.websocket.impl;

import com.elinext.crypto.dto.WebSocketViewDto;
import com.elinext.crypto.service.websocket.WebSocketServerService;
import com.elinext.crypto.web.Navigation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketServerServiceImpl implements WebSocketServerService {

	private final SimpMessagingTemplate messagingTemplate;

	@Override
	public void appeared(WebSocketViewDto view) {

		messagingTemplate.convertAndSend(Navigation.APPEARED, view);
	}

	@Override
	public void removed(String uid) {

		messagingTemplate.convertAndSend(Navigation.REMOVED, uid);
	}
}
