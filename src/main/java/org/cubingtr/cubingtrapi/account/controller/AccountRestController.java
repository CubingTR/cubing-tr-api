package org.cubingtr.cubingtrapi.account.controller;

import lombok.RequiredArgsConstructor;
import org.cubingtr.cubingtrapi.account.entity.AccountCompetitionRegistrationEntity;
import org.cubingtr.cubingtrapi.account.model.AccountCompetitionRegistrationRequest;
import org.cubingtr.cubingtrapi.account.service.AccountRestControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountRestController {

	private final AccountRestControllerService accountRestControllerService;

	@PostMapping("/register")
	public ResponseEntity<List<AccountCompetitionRegistrationEntity>> register(@RequestBody AccountCompetitionRegistrationRequest accountCompetitionRegistrationRequest) {
		return ResponseEntity.ok(accountRestControllerService.register(accountCompetitionRegistrationRequest));
	}

}
