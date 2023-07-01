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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wca-competition")
@RequiredArgsConstructor
public class WcaCompetitionController {

	private final WcaCompetitionRepository wcaCompetitionRepository;
	private final WcaCompetitionEventRepository competitionEventRepository;

	@GetMapping("/{competitionId}")
	public ResponseEntity<WcaCompetitionEntity> getCompetition(@PathVariable("competitionId") String competitionId) {
		WcaCompetitionEntity wcaCompetitionEntity = wcaCompetitionRepository.findById(competitionId);

		wcaCompetitionEntity.setCompetitionEventEntityList(competitionEventRepository.findAllByCompetitionId(wcaCompetitionEntity.getId()));

		return ResponseEntity.ok(wcaCompetitionEntity);
	}


	@GetMapping("/future")
	public ResponseEntity<List<WcaCompetitionEntity>> futureCompetitions() {
		//return ResponseEntity.ok(wcaCompetitionRepository.findAllByCountryidAndStartDateAfter("Turkey", LocalDate.now()));
		return ResponseEntity.ok(wcaCompetitionRepository.findAllByCountryid("Turkey")
				.stream()
				.filter(wcaCompetitionEntity -> LocalDate.now().isBefore(LocalDate.of(wcaCompetitionEntity.getYear(), wcaCompetitionEntity.getMonth(), wcaCompetitionEntity.getDay())))
				.collect(Collectors.toList())
				.stream().sorted((comp1, comp2) -> LocalDate.of(comp1.getYear(), comp1.getMonth(), comp1.getDay()).compareTo(LocalDate.of(comp2.getYear(), comp2.getMonth(), comp2.getDay()))) // ascending order
				.collect(Collectors.toList())
		);
	}

	@GetMapping("/past")
	public ResponseEntity<List<WcaCompetitionEntity>> pastCompetitions() {
		//return ResponseEntity.ok(wcaCompetitionRepository.findAllByCountryidAndStartDateBefore("Turkey", LocalDate.now()));
		return ResponseEntity.ok(wcaCompetitionRepository.findAllByCountryid("Turkey")
				.stream()
				.filter(wcaCompetitionEntity -> LocalDate.now().isAfter(LocalDate.of(wcaCompetitionEntity.getYear(), wcaCompetitionEntity.getMonth(), wcaCompetitionEntity.getDay())))
				.collect(Collectors.toList())
				.stream().sorted((comp1, comp2) -> LocalDate.of(comp2.getYear(), comp2.getMonth(), comp2.getDay()).compareTo(LocalDate.of(comp1.getYear(), comp1.getMonth(), comp1.getDay()))) // descending order
				.collect(Collectors.toList())
		);
	}

	@GetMapping(value = "/event")
	public ResponseEntity<List<WcaCompetitionEventEntity>> getCompetitionEvents(@RequestParam("competitionId") String competitionId) {
		return ResponseEntity.ok(competitionEventRepository.findAllByCompetitionId(competitionId));
	}


}
