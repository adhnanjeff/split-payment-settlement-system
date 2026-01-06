package com.jeff.services;

import com.jeff.dtos.RuleSetRequestDto;
import com.jeff.dtos.RuleSetResponseDto;
import com.jeff.entities.RuleSet;
import com.jeff.repositories.RuleSetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RuleSetService {

    private final RuleSetRepository ruleSetRepo;

    public RuleSetService(RuleSetRepository ruleSetRepo) {
        this.ruleSetRepo = ruleSetRepo;
    }

    public List<RuleSetResponseDto> findAllRuleSets() {
        return ruleSetRepo.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public RuleSetResponseDto findRuleSetById(UUID id) {
        RuleSet ruleSet = ruleSetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("RuleSet with id " + id + " not found"));
        return toResponseDto(ruleSet);
    }

    public void deleteRuleSetById(UUID id) {
        ruleSetRepo.deleteById(id);
    }

    public RuleSetResponseDto createRuleSet(RuleSetRequestDto requestDto) {
        RuleSet ruleSet = toEntity(requestDto);
        RuleSet savedRuleSet = ruleSetRepo.save(ruleSet);
        return toResponseDto(savedRuleSet);
    }

    public RuleSetResponseDto updateRuleSet(UUID id, RuleSetRequestDto requestDto) {
        RuleSet existingRuleSet = ruleSetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("RuleSet with id " + id + " not found"));

        existingRuleSet.setName(requestDto.getName());
        existingRuleSet.setDescription(requestDto.getDescription());
        existingRuleSet.setMode(requestDto.getMode());

        RuleSet savedRuleSet = ruleSetRepo.save(existingRuleSet);
        return toResponseDto(savedRuleSet);
    }

    private RuleSetResponseDto toResponseDto(RuleSet ruleSet) {
        return new RuleSetResponseDto(
                ruleSet.getRuleSetId(),
                ruleSet.getName(),
                ruleSet.getDescription(),
                ruleSet.getMode(),
                ruleSet.getCreatedAt()
        );
    }

    private RuleSet toEntity(RuleSetRequestDto requestDto) {
        return new RuleSet(
                requestDto.getName(),
                requestDto.getDescription(),
                requestDto.getMode()
        );
    }
}