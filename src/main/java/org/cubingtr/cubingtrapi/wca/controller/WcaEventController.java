package org.cubingtr.cubingtrapi.wca.controller;

import lombok.RequiredArgsConstructor;
import org.cubingtr.cubingtrapi.wca.entity.WcaEventEntity;
import org.cubingtr.cubingtrapi.wca.repository.WcaEventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wca-event")
@RequiredArgsConstructor
public class WcaEventController {

	private final WcaEventRepository wcaEventRepository;

	@GetMapping
	public ResponseEntity<List<WcaEventEntity>> events() {
		return ResponseEntity.ok(wcaEventRepository.findAll());
	}

}
