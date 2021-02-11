package com.elinext.crypto.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum WebSocketChannelType {

	SUBSCRIBE("subscribe"),
	SUBSCRIBED("subscribed"),
	DATA("data"),
	OFFLINE("offline");

	private final String text;

	@JsonValue
	public String text() {

		return text;
	}
}
