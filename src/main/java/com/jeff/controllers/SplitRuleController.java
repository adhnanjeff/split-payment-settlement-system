package com.jeff.controllers;

import com.jeff.dtos.SplitRuleRequestDto;
import com.jeff.dtos.SplitRuleResponseDto;
import com.jeff.services.SplitRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/splitrules")
public class SplitRuleController {
    
    private final SplitRuleService splitRuleService;
    
    public SplitRuleController(SplitRuleService splitRuleService) {
        this.splitRuleService = splitRuleService;
    }

    @GetMapping
    public List<SplitRuleResponseDto> getAllSplitRules() {
        return splitRuleService.findAllSplitRules();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SplitRuleResponseDto> getSplitRuleById(@PathVariable UUID id) {
        SplitRuleResponseDto splitRule = splitRuleService.findSplitRuleById(id);
        return ResponseEntity.ok(splitRule);
    }
    
    @PostMapping
    public SplitRuleResponseDto createSplitRule(@RequestBody SplitRuleRequestDto requestDto) {
        return splitRuleService.createSplitRule(requestDto);
    }
    
    @PutMapping("/{id}")
    public SplitRuleResponseDto updateSplitRule(@PathVariable UUID id, @RequestBody SplitRuleRequestDto requestDto) {
        return splitRuleService.updateSplitRule(id, requestDto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSplitRule(@PathVariable UUID id) {
        splitRuleService.deleteSplitRuleById(id);
        return ResponseEntity.noContent().build();
    }
}