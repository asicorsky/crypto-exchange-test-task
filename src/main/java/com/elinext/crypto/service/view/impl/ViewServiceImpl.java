package com.elinext.crypto.service.view.impl;

import com.elinext.crypto.dto.WebSocketViewDto;
import com.elinext.crypto.service.provider.ViewDataHandler;
import com.elinext.crypto.service.view.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {

	private final ViewDataHandler dataHandler;

	@Override
	public List<WebSocketViewDto> getBuys() {

		return dataHandler.buys();
	}

	@Override
	public List<WebSocketViewDto> getSells() {

		return dataHandler.sells();
	}
}
