package com.elinext.crypto.service.provider.impl;

import com.elinext.crypto.dto.Side;
import com.elinext.crypto.dto.WebSocketChannelDataDto;
import com.elinext.crypto.dto.WebSocketViewDto;
import com.elinext.crypto.service.provider.ViewDataHandler;
import com.elinext.crypto.service.websocket.WebSocketServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@RequiredArgsConstructor
public class ViewDataHandlerImpl implements ViewDataHandler {

	private final Lock buysLock = new ReentrantLock();
	private final Lock sellsLock = new ReentrantLock();

	private final NavigableMap<BigDecimal, WebSocketViewDto> buysMap = new TreeMap<>(Comparator.reverseOrder());
	private final NavigableMap<BigDecimal, WebSocketViewDto> sellsMap = new TreeMap<>();

	private final WebSocketServerService webSocketServerService;

	@Override
	public List<WebSocketViewDto> buys() {

		try {
			buysLock.lock();
			return valuesAsList(buysMap);
		} finally {
			buysLock.unlock();
		}
	}

	@Override
	public List<WebSocketViewDto> sells() {

		try {
			sellsLock.lock();
			return valuesAsList(sellsMap);
		} finally {
			sellsLock.unlock();
		}
	}

	@Override
	public void handle(WebSocketChannelDataDto data) {

		Side side = data.getSide();
		if (Side.BUY.equals(side)) {
			try {
				buysLock.lock();
				operate(buysMap, data);
			} finally {
				buysLock.unlock();
			}
		} else {
			try {
				sellsLock.lock();
				operate(sellsMap, data);
			} finally {
				sellsLock.unlock();
			}
		}
	}

	private void operate(NavigableMap<BigDecimal, WebSocketViewDto> map, WebSocketChannelDataDto data) {

		WebSocketViewDto view = calculate(map, data);
		String putAfter = Optional.ofNullable(map.higherEntry(data.getPrice())).map(entry -> entry.getValue().getUid()).orElse(null);
		view.setPreviousUid(putAfter);
		if (map.size() == 51) {
			BigDecimal last = map.lastKey();
			WebSocketViewDto lastView = map.remove(last);
			String uid = lastView.getUid();
			if (!view.getUid().equals(uid)) {
				webSocketServerService.appeared(view);
				webSocketServerService.removed(uid);
			}
		} else {
			webSocketServerService.appeared(view);
		}
	}

	private static WebSocketViewDto calculate(Map<BigDecimal, WebSocketViewDto> map, WebSocketChannelDataDto data) {

		BigDecimal size = data.getSize();
		BigDecimal price = data.getPrice();
		map.computeIfAbsent(price, bigDecimal -> new WebSocketViewDto(UUID.randomUUID().toString(), data.getSide(), price, BigDecimal.ZERO, BigDecimal.ZERO, null));
		WebSocketViewDto view = map.get(price);
		BigDecimal newSize = size.add(view.getSize());
		BigDecimal newSummary = price.multiply(newSize);
		view.setSize(newSize);
		view.setSummary(newSummary);
		return view;
	}

	private static List<WebSocketViewDto> valuesAsList(Map<BigDecimal, WebSocketViewDto> map) {

		var list = new ArrayList<WebSocketViewDto>();
		// map.values() may miss order
		map.forEach((__, value) -> list.add(value));
		return list;
	}
}
