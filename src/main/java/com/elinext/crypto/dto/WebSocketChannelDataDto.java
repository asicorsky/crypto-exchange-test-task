package com.elinext.crypto.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class WebSocketChannelDataDto {

	BigDecimal price;

	Side side;

	BigDecimal size;

	String market;
}
