package com.elinext.crypto.service.provider;

import com.elinext.crypto.dto.WebSocketChannelDataDto;
import com.elinext.crypto.dto.WebSocketViewDto;

import java.util.List;

public interface ViewDataHandler {

	List<WebSocketViewDto> buys();

	List<WebSocketViewDto> sells();

	void handle(WebSocketChannelDataDto data);
}
