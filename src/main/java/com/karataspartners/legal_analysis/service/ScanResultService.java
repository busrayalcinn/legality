package com.karataspartners.legal_analysis.service;

import com.karataspartners.legal_analysis.entity.ScanResult;
import com.karataspartners.legal_analysis.repository.ScanResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ScanResultService {

    private final ScanResultRepository scanResultRepository;

    // Tüm tarama sonuçlarını getiren metot
    public ResponseEntity<List<ScanResult>> findAll() {
        List<ScanResult> scanResults = scanResultRepository.findAll();
        return ResponseEntity.ok(scanResults);
    }

    // ID ile tarama sonucu bulan metot
    public ResponseEntity<ScanResult> findById(Long id) {
        Optional<ScanResult> scanResult = scanResultRepository.findById(id);
        return scanResult.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Belirli bir tarih aralığındaki tarama sonuçlarını getiren metot (Özel sorgu)
    public ResponseEntity<List<ScanResult>> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<ScanResult> scanResults = scanResultRepository.findByScanDateBetween(startDate, endDate);
        return ResponseEntity.ok(scanResults);
    }

    // Yeni tarama sonucu kaydeden metot
    public ResponseEntity<ScanResult> save(ScanResult scanResult) {
        ScanResult savedScanResult = scanResultRepository.save(scanResult);
        return ResponseEntity.ok(savedScanResult);
    }

    // Tarama sonucunu ID ile silen metot
    public ResponseEntity<Void> deleteById(Long id) {
        scanResultRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
