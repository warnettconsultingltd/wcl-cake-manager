package com.waracle.cakemgr.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CakeRepository extends JpaRepository<CakeEntity, UUID> {
    boolean existsByTitle(String title);
}
