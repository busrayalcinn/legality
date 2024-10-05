package com.karataspartners.legal_analysis.repository;

import com.karataspartners.legal_analysis.entity.Website;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WebsiteRepository extends JpaRepository<Website, Long> {

    Optional<Website> findByUrl(String url);

    List<Website> findByOwnerName(String ownerName);
}
