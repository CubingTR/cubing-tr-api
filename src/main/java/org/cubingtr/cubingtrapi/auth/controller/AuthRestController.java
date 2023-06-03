package org.cubingtr.cubingtrapi.auth.controller;

import lombok.RequiredArgsConstructor;
import org.cubingtr.cubingtrapi.auth.model.AccountDto;
import org.cubingtr.cubingtrapi.auth.model.LoginResponse;
import org.cubingtr.cubingtrapi.auth.service.UserControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {

	private final UserControllerService userControllerService;

	@GetMapping("/login")
	public ResponseEntity<LoginResponse> auth(@RequestParam("wcaAuthCode") String wcaAuthCode) {
		return ResponseEntity.ok(userControllerService.login(wcaAuthCode));
	}

	@GetMapping("/me")
	public ResponseEntity<AccountDto> me() {
		return ResponseEntity.ok(userControllerService.me());
	}

}
