package org.cubingtr.cubingtrapi.wca.controller;

import lombok.RequiredArgsConstructor;
import org.cubingtr.cubingtrapi.wca.entity.WcaCompetitionEntity;
import org.cubingtr.cubingtrapi.wca.entity.WcaCompetitionEventEntity;
import org.cubingtr.cubingtrapi.wca.entity.WcaEventEntity;
import org.cubingtr.cubingtrapi.wca.repository.WcaCompetitionEventRepository;
import org.cubingtr.cubingtrapi.wca.repository.WcaCompetitionRepository;
import org.cubingtr.cubingtrapi.wca.repository.WcaEventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/wca-competition")
@RequiredArgsConstructor
public class WcaCompetitionController {

	private final WcaCompetitionRepository wcaCompetitionRepository;
	private final WcaCompetitionEventRepository competitionEventRepository;

	@GetMapping("/future")
	public ResponseEntity<List<WcaCompetitionEntity>> futureCompetitions() {
		return ResponseEntity.ok(wcaCompetitionRepository.findAllByCountryidAndStartDateAfter("Turkey", LocalDate.now()));
	}

	@GetMapping("/past")
	public ResponseEntity<List<WcaCompetitionEntity>> pastCompetitions() {
		//return ResponseEntity.ok(wcaCompetitionRepository.findAllByCountryidAndStartDateBefore("Turkey", LocalDate.now()));
		return ResponseEntity.ok(wcaCompetitionRepository.findAllByCountryid("Turkey"));
	}

	@GetMapping(value = "/event")
	public ResponseEntity<List<WcaCompetitionEventEntity>> getCompetitionEvents(@RequestParam("competitionId") String competitionId) {
		return ResponseEntity.ok(competitionEventRepository.findAllByCompetitionId(competitionId));
	}


}
