package com.karataspartners.legal_analysis.service;

import com.karataspartners.legal_analysis.entity.LegalDocument;
import com.karataspartners.legal_analysis.repository.LegalDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LegalDocumentService {

    private final LegalDocumentRepository legalDocumentRepository;

    // Tüm yasal belgeleri getiren metot
    public ResponseEntity<List<LegalDocument>> findAll() {
        List<LegalDocument> legalDocuments = legalDocumentRepository.findAll();
        return ResponseEntity.ok(legalDocuments);
    }

    // ID ile yasal belge bulan metot
    public ResponseEntity<LegalDocument> findById(Long id) {
        Optional<LegalDocument> legalDocument = legalDocumentRepository.findById(id);
        return legalDocument.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tamamlanmamış yasal belgeleri getiren metot (Özel sorgu)
    public ResponseEntity<List<LegalDocument>> findIncompleteDocuments() {
        List<LegalDocument> incompleteDocuments = legalDocumentRepository.findByIsCompleteFalse();
        return ResponseEntity.ok(incompleteDocuments);
    }

    // Yeni yasal belge ekleyen metot
    public ResponseEntity<LegalDocument> save(LegalDocument legalDocument) {
        LegalDocument savedDocument = legalDocumentRepository.save(legalDocument);
        return ResponseEntity.ok(savedDocument);
    }

    // Yasal belgeyi ID ile silen metot
    public ResponseEntity<Void> deleteById(Long id) {
        legalDocumentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
