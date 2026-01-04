package com.jeff.controllers;

import com.jeff.dtos.RuleSetRequestDto;
import com.jeff.dtos.RuleSetResponseDto;
import com.jeff.services.RuleSetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rulesets")
public class RuleSetController {
    
    private final RuleSetService ruleSetService;
    
    public RuleSetController(RuleSetService ruleSetService) {
        this.ruleSetService = ruleSetService;
    }

    @GetMapping
    public List<RuleSetResponseDto> getAllRuleSets() {
        return ruleSetService.findAllRuleSets();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RuleSetResponseDto> getRuleSetById(@PathVariable UUID id) {
        RuleSetResponseDto ruleSet = ruleSetService.findRuleSetById(id);
        return ResponseEntity.ok(ruleSet);
    }
    
    @PostMapping
    public RuleSetResponseDto createRuleSet(@RequestBody RuleSetRequestDto requestDto) {
        return ruleSetService.createRuleSet(requestDto);
    }
    
    @PutMapping("/{id}")
    public RuleSetResponseDto updateRuleSet(@PathVariable UUID id, @RequestBody RuleSetRequestDto requestDto) {
        return ruleSetService.updateRuleSet(id, requestDto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRuleSet(@PathVariable UUID id) {
        ruleSetService.deleteRuleSetById(id);
        return ResponseEntity.noContent().build();
    }
}