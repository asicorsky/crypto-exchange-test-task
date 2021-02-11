package com.elinext.crypto.utils;

import com.elinext.crypto.dto.WebSocketChannelDto;
import com.elinext.crypto.dto.WebSocketChannelType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public interface WebSocketUtils {

	String DEFAULT_CHANNEL_NAME = "orderbook";
	String DEFAULT_SUB_CHANNEL_NAME = "btc-eur";
	DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE,' 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'z");
	String UTC = "UTC";
	String DATE_HEADER = "Date";
	String API_KEY_HEADER = "ApiKey";
	String AUTHORIZATION_HEADER = "Authorization";

	@SneakyThrows
	static TextMessage subscribeMessage(ObjectMapper objectMapper) {

		WebSocketChannelDto subscribe = WebSocketChannelDto.builder().type(WebSocketChannelType.SUBSCRIBE).channelName(DEFAULT_CHANNEL_NAME)
				.subChannelName(DEFAULT_SUB_CHANNEL_NAME).build();
		String payload = objectMapper.writeValueAsString(subscribe);
		return new TextMessage(payload);
	}

	@SneakyThrows
	static WebSocketChannelDto asChannel(TextMessage message, ObjectMapper objectMapper) {

		String payload = message.getPayload();
		return objectMapper.readValue(payload, WebSocketChannelDto.class);
	}

	static WebSocketHttpHeaders subscribeHeaders(String key, String secret) {

		String date = ZonedDateTime.now(ZoneId.of(UTC)).format(FORMATTER);
		byte[] hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, secret).hmac("date: " + date);
		String signature = Base64.getEncoder().encodeToString(hmac);
		String authorization = "hmac username=\"" + key + "\", algorithm=\"hmac-sha1\", headers=\"date\", signature=\"" + signature + "\"";
		WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
		headers.add(DATE_HEADER, date);
		headers.add(API_KEY_HEADER, key);
		headers.add(AUTHORIZATION_HEADER, authorization);
		return headers;
	}
}
