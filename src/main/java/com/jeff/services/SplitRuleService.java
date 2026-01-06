package com.jeff.services;

import com.jeff.dtos.SplitRuleRequestDto;
import com.jeff.dtos.SplitRuleResponseDto;
import com.jeff.entities.RuleSet;
import com.jeff.entities.SplitRule;
import com.jeff.repositories.RuleSetRepository;
import com.jeff.repositories.SplitRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SplitRuleService {

    private final SplitRuleRepository splitRuleRepo;
    private final RuleSetRepository ruleSetRepo;

    public SplitRuleService(SplitRuleRepository splitRuleRepo, RuleSetRepository ruleSetRepo) {
        this.splitRuleRepo = splitRuleRepo;
        this.ruleSetRepo = ruleSetRepo;
    }

    public List<SplitRuleResponseDto> findAllSplitRules() {
        return splitRuleRepo.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public SplitRuleResponseDto findSplitRuleById(UUID id) {
        SplitRule splitRule = splitRuleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("SplitRule with id " + id + " not found"));
        return toResponseDto(splitRule);
    }

    public void deleteSplitRuleById(UUID id) {
        splitRuleRepo.deleteById(id);
    }

    public SplitRuleResponseDto createSplitRule(SplitRuleRequestDto requestDto) {
        RuleSet ruleSet = ruleSetRepo.findById(requestDto.getRuleSetId())
                .orElseThrow(() -> new RuntimeException("RuleSet not found"));
        
        SplitRule splitRule = toEntity(requestDto, ruleSet);
        SplitRule savedSplitRule = splitRuleRepo.save(splitRule);
        return toResponseDto(savedSplitRule);
    }

    public SplitRuleResponseDto updateSplitRule(UUID id, SplitRuleRequestDto requestDto) {
        SplitRule existingSplitRule = splitRuleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("SplitRule with id " + id + " not found"));
        
        RuleSet ruleSet = ruleSetRepo.findById(requestDto.getRuleSetId())
                .orElseThrow(() -> new RuntimeException("RuleSet not found"));

        existingSplitRule.setRuleSet(ruleSet);
        existingSplitRule.setReceiverAccountId(requestDto.getReceiverAccountId());
        existingSplitRule.setPercentage(requestDto.getPercentage());
        existingSplitRule.setAmount(requestDto.getAmount());

        SplitRule savedSplitRule = splitRuleRepo.save(existingSplitRule);
        return toResponseDto(savedSplitRule);
    }

    private SplitRuleResponseDto toResponseDto(SplitRule splitRule) {
        return new SplitRuleResponseDto(
                splitRule.getRuleId(),
                splitRule.getRuleSet().getRuleSetId(),
                splitRule.getReceiverAccountId(),
                splitRule.getPercentage(),
                splitRule.getAmount(),
                splitRule.getCreatedAt()
        );
    }

    private SplitRule toEntity(SplitRuleRequestDto requestDto, RuleSet ruleSet) {
        return new SplitRule(
                ruleSet,
                requestDto.getReceiverAccountId(),
                requestDto.getPercentage(),
                requestDto.getAmount()
        );
    }
}