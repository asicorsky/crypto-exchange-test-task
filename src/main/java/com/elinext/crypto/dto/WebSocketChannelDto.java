package com.elinext.crypto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class WebSocketChannelDto {

	WebSocketChannelType type;

	@JsonProperty("chan_name")
	String channelName;

	@JsonProperty("subchan_name")
	String subChannelName;

	List<WebSocketChannelDataDto> data;
}
