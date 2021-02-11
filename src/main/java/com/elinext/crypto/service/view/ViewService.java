package com.elinext.crypto.service.view;

import com.elinext.crypto.dto.WebSocketViewDto;

import java.util.List;

public interface ViewService {

	List<WebSocketViewDto> getBuys();

	List<WebSocketViewDto> getSells();
}
