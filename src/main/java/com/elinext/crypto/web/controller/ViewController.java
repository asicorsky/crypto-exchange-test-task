package com.elinext.crypto.web.controller;

import com.elinext.crypto.service.view.ViewService;
import com.elinext.crypto.web.Navigation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Navigation.VIEW)
@RequiredArgsConstructor
public class ViewController {

	private final ViewService viewService;

	@GetMapping(Navigation.BUY)
	public ResponseEntity<?> getBuys() {

		var buys = viewService.getBuys();
		return ResponseEntity.ok(buys);
	}

	@GetMapping(Navigation.SELL)
	public ResponseEntity<?> getSells() {

		var sells = viewService.getSells();
		return ResponseEntity.ok(sells);
	}
}
