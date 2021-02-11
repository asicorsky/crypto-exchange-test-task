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

	private Credentials credentials = new Credentials();
	private WebSocket webSocket = new WebSocket();
	private Rest rest = new Rest();

	@Getter
	@Setter
	public static class Credentials {

		private String apiKey;

		private String apiSecret;
	}

	@Getter
	@Setter
	public static class WebSocket {

		private String connectionUrl;
	}

	@Getter
	@Setter
	public static class Rest {

		private String connectionUrl;
	}
}
