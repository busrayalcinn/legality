package com.karataspartners.legal_analysis.service;

import com.karataspartners.legal_analysis.entity.Website;
import com.karataspartners.legal_analysis.repository.WebsiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WebsiteService {

    private final WebsiteRepository websiteRepository;

    // Tüm web sitelerini getiren metot
    public List<Website> findAll() {
        return websiteRepository.findAll();
    }

    // ID ile web sitesi bulan metot
    public Optional<Website> findById(Long id) {
        return websiteRepository.findById(id);
    }

    // URL ile web sitesi bulan metot (Özel sorgu)
    public Optional<Website> findByUrl(String url) {
        return websiteRepository.findByUrl(url);
    }

    // Yeni web sitesi kaydeden metot
    public Website save(Website website) {
        return websiteRepository.save(website);
    }

    // Web sitesini ID ile silen metot
    public void deleteById(Long id) {
        websiteRepository.deleteById(id);
    }

    // Şirket sahibine göre web sitelerini getiren metot
    public List<Website> findByOwnerName(String ownerName) {
        return websiteRepository.findByOwnerName(ownerName);
    }

    // Web sitesi içerik analizi yapan metot
    public ResponseEntity<String> analyzeUrl(String content) {
        try {
            // Örnek analiz işlemi
            String result = "Analysis completed for content: " + content;
            return ResponseEntity.ok(result);  // Başarılı sonuç döndürülüyor
        } catch (Exception e) {
            // Hata yönetimi
            System.err.println("Error analyzing URL: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error analyzing the URL.");
        }
    }
}
