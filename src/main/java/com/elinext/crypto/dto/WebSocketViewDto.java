package com.elinext.crypto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class WebSocketViewDto {

	private final String uid;

	private final Side side;

	private final BigDecimal price;

	private BigDecimal size;

	private BigDecimal summary;

	private String previousUid;
}
