package com.elinext.crypto.configuration;

import com.elinext.crypto.service.rest.TribeWebClient;
import com.elinext.crypto.utils.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class RestConfiguration {

	private final ApplicationProperties applicationProperties;

	@Bean
	@TribeWebClient
	public WebClient webClient() {

		ApplicationProperties.Credentials credentials = applicationProperties.getCredentials();
		ApplicationProperties.Rest rest = applicationProperties.getRest();
		return WebClient.builder()/*.codecs(configurer -> {
			configurer.customCodecs().register(new Jackson2JsonDecoder());
			configurer.customCodecs().register(new Jackson2JsonEncoder());
		})*/.baseUrl(rest.getConnectionUrl()).defaultHeaders(headers -> HttpUtils.fillHttpHeaders(headers, credentials.getApiKey(), credentials.getApiSecret())).build();
	}
}
