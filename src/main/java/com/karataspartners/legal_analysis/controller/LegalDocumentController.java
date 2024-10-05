package com.karataspartners.legal_analysis.controller;

import com.karataspartners.legal_analysis.entity.LegalDocument;
import com.karataspartners.legal_analysis.service.LegalDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/legal-documents")
@RequiredArgsConstructor
public class LegalDocumentController {

    private final LegalDocumentService legalDocumentService;

    // Tüm yasal belgeleri getiren metot
    @GetMapping
    public ResponseEntity<List<LegalDocument>> getAllLegalDocuments() {
        return legalDocumentService.findAll();
    }

    // ID ile yasal belge bulan metot
    @GetMapping("/{id}")
    public ResponseEntity<LegalDocument> getLegalDocumentById(@PathVariable Long id) {
        return legalDocumentService.findById(id);
    }

    // Tamamlanmamış yasal belgeleri getiren metot
    @GetMapping("/incomplete")
    public ResponseEntity<List<LegalDocument>> getIncompleteLegalDocuments() {
        return legalDocumentService.findIncompleteDocuments();
    }

    // Yeni yasal belge ekleyen metot
    @PostMapping
    public ResponseEntity<LegalDocument> createLegalDocument(@RequestBody LegalDocument legalDocument) {
        return legalDocumentService.save(legalDocument);
    }

    // Yasal belgeyi ID ile silen metot
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLegalDocument(@PathVariable Long id) {
        return legalDocumentService.deleteById(id);
    }
}
