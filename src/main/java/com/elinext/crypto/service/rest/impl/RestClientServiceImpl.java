package com.elinext.crypto.service.rest.impl;

import com.elinext.crypto.dto.RestResponseDto;
import com.elinext.crypto.service.rest.RestClientService;
import com.elinext.crypto.service.rest.TribeWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class RestClientServiceImpl implements RestClientService {

	private final WebClient webClient;

	@Autowired
	public RestClientServiceImpl(@TribeWebClient WebClient webClient) {

		this.webClient = webClient;
	}

	@Override
	public void connect() {

		webClient.get().retrieve().bodyToFlux(RestResponseDto.class).toStream().forEach(object -> log.info("Returned from REST :: {}", object));
	}
}
