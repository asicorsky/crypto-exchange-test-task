package com.elinext.crypto.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
@Getter
@Setter
public class ApplicationProperties {

	private WebSocket webSocket = new WebSocket();

	@Getter
	@Setter
	public static class WebSocket {

		private String apiKey;

		private String apiSecret;

		private String connectionUrl;
	}
}
