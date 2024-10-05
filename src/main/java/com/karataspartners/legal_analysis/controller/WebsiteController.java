package com.karataspartners.legal_analysis.controller;

import com.karataspartners.legal_analysis.entity.Website;
import com.karataspartners.legal_analysis.service.WebsiteService;
import com.karataspartners.legal_analysis.service.WebScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/websites")
@RequiredArgsConstructor
public class WebsiteController {

    private final WebsiteService websiteService;
    private final WebScraperService webScraperService;

    // Tüm web sitelerini getiren metot
    @GetMapping
    public ResponseEntity<List<Website>> getAllWebsites() {
        List<Website> websites = websiteService.findAll();
        return ResponseEntity.ok(websites);
    }

    // ID ile web sitesi bulan metot
    @GetMapping("/{id}")
    public ResponseEntity<Website> getWebsiteById(@PathVariable Long id) {
        return websiteService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // URL ile web sitesi bulan metot (Özel sorgu)
    @GetMapping("/url")
    public ResponseEntity<Website> getWebsiteByUrl(@RequestParam String url) {
        return websiteService.findByUrl(url)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Yeni web sitesi ekleyen metot
    @PostMapping
    public ResponseEntity<Website> createWebsite(@RequestBody Website website) {
        Website createdWebsite = websiteService.save(website);
        return ResponseEntity.status(201).body(createdWebsite); // 201 Created
    }

    // Web sitesini ID ile silen metot
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWebsite(@PathVariable Long id) {
        websiteService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Şirket sahibi ismine göre web sitelerini getiren endpoint
    @GetMapping("/owner")
    public ResponseEntity<List<Website>> getWebsitesByOwnerName(@RequestParam String ownerName) {
        List<Website> websites = websiteService.findByOwnerName(ownerName);
        if (websites.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(websites);
    }

    // URL analiz eden metot
    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeUrl(@RequestParam String url) {
        // WebScraperService ile URL'nin içeriğini al
        String content = webScraperService.getContentFromUrl(url);
        return websiteService.analyzeUrl(content); // İçeriği analiz et
    }
}
