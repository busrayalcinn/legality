package com.karataspartners.legal_analysis.repository;

import com.karataspartners.legal_analysis.entity.LegalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// LegalDocument entity'si için repository arayüzü
@Repository
public interface LegalDocumentRepository extends JpaRepository<LegalDocument, Long> {

    // Tamamlanmamış yasal belgeleri getiren sorgu
    List<LegalDocument> findByIsCompleteFalse();
}
