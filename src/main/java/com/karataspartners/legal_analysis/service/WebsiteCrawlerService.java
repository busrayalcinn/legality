package com.karataspartners.legal_analysis.service;

import com.karataspartners.legal_analysis.dto.OpenAiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
@RequiredArgsConstructor
@Service
public class WebsiteCrawlerService {

    private final RestTemplate restTemplate;
    private final OpenAiRequest openAiRequest;



    // Web sitesi içeriğini çeken metod
    public String crawlWebsite(String url) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody(); // HTML içeriğini döndürüyoruz
        } catch (Exception e) {
            // Hata durumunda log tutabilir ve özel bir hata mesajı dönebilirsiniz
            System.err.println("Hata: " + e.getMessage());
            return null; // Hata durumunda null döneriz
        }
    }

    // GPT ile uyumluluk denetimi yapan metod
    public Map<String, String> analyzeComplianceWithGPT(String siteContent) {
        Map<String, String> complianceReports = new HashMap<>();

        // Düzenlemeler
        String[] regulations = {"GDPR", "CCPA", "HIPAA"};
        for (String regulation : regulations) {
            String analysis = checkComplianceWithGPT(siteContent, regulation);
            complianceReports.put(regulation, analysis);
        }

        return complianceReports;
    }

    // GPT'ye istek yapan fonksiyon
    private String checkComplianceWithGPT(String siteContent, String regulation) {
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("API anahtarı ayarlanmamış!");
            return "API anahtarı ayarlanmamış.";
        }

        try {
            // OpenAI API çağrısı yapılıyor
            OpenAiRequest request = new OpenAiRequest();
            String response = request.sendComplianceRequest(siteContent, regulation);
            return response;

        } catch (Exception e) {
            System.err.println("Hata: " + e.getMessage());
            return "Uyumluluk denetimi sırasında hata oluştu.";
        }
    }
}
