package com.karataspartners.legal_analysis.controller;

import com.karataspartners.legal_analysis.service.WebsiteCrawlerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("/api/crawler")  // Base path olarak api/crawler kullanıyoruz
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")  // Frontend portu
public class WebsiteCrawlerController {

    private final WebsiteCrawlerService websiteCrawlerService;

    // URL geçerliliğini kontrol eden fonksiyon
    private boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/crawl") // URL: http://localhost:8080/api/crawler/crawl?url={your-url}
    public ResponseEntity<Map<String, Object>> crawlWebsite(@RequestParam String url) {
        if (!isValidURL(url)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("hata", "Lütfen geçerli bir URL girin. URL'niz 'http://' veya 'https://' ile başlamalıdır."));
        }

        try {
            // Web sitesinin HTML içeriğini alıyoruz
            String content = websiteCrawlerService.crawlWebsite(url);

            if (content != null && !content.isEmpty()) {
                // GPT ile uyumluluk analizini yapıyoruz
                Map<String, String> complianceReports = websiteCrawlerService.analyzeComplianceWithGPT(content);

                // Sonuçları kullanıcıya döndürüyoruz
                Map<String, Object> response = new HashMap<>();
                response.put("uyumlulukRaporu", complianceReports);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("hata", "İçerik alınamadı."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("hata", "Bir hata oluştu: " + e.getMessage()));
        }
    }
}
