package com.karataspartners.legal_analysis.repository;

import com.karataspartners.legal_analysis.entity.ScanResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// ScanResult entity'si için repository arayüzü
@Repository
public interface ScanResultRepository extends JpaRepository<ScanResult, Long> {

    // Belirli bir tarih aralığındaki tarama sonuçlarını getiren sorgu
    List<ScanResult> findByScanDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
