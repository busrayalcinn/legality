package com.karataspartners.legal_analysis.controller;

import com.karataspartners.legal_analysis.entity.ScanResult;
import com.karataspartners.legal_analysis.service.ScanResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/scan-results")
@RequiredArgsConstructor
public class ScanResultController {

    private final ScanResultService scanResultService;

    // Tüm tarama sonuçlarını getiren metot
    @GetMapping
    public ResponseEntity<List<ScanResult>> getAllScanResults() {
        return scanResultService.findAll();
    }

    // ID ile tarama sonucu bulan metot
    @GetMapping("/{id}")
    public ResponseEntity<ScanResult> getScanResultById(@PathVariable Long id) {
        return scanResultService.findById(id);
    }

    // Belirli bir tarih aralığındaki tarama sonuçlarını getiren metot
    @GetMapping("/by-date")
    public ResponseEntity<List<ScanResult>> getScanResultsByDateRange(
            @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return scanResultService.findByDateRange(startDate, endDate);
    }

    // Yeni tarama sonucu ekleyen metot
    @PostMapping
    public ResponseEntity<ScanResult> createScanResult(@RequestBody ScanResult scanResult) {
        return scanResultService.save(scanResult);
    }

    // Tarama sonucunu ID ile silen metot
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScanResult(@PathVariable Long id) {
        return scanResultService.deleteById(id);
    }
}
