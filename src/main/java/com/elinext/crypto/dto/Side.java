package com.elinext.crypto.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Side {

	BUY("buy"),
	SELL("sell");

	private final String text;

	@JsonValue
	public String text() {

		return text;
	}
}
