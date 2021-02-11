package com.elinext.crypto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestResponseDto {

	@JsonProperty("asset_id")
	private String assetId;

	private BigDecimal available;

	private BigDecimal locked;

	private BigDecimal credit;
}
