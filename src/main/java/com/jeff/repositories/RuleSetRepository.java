package com.jeff.repositories;

import com.jeff.entities.RuleSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RuleSetRepository extends JpaRepository<RuleSet, UUID> {
}