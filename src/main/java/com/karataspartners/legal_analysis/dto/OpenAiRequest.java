package com.karataspartners.legal_analysis.dto;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OpenAiRequest {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    // Mesajın boyutunu ve token limitini göz önünde bulundurarak veriyi küçültelim
    public String sendComplianceRequest(String siteContent, String regulation) {
        String apiKey = System.getenv("OPENAI_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("API anahtarı ayarlanmamış!");
            return "API anahtarı ayarlanmamış.";
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // Gönderilen siteContent ve regulation boyutlarını kontrol edin ve küçük parçalara ayırın
            String shortenedSiteContent = siteContent.substring(0, Math.min(siteContent.length(), 2000)); // İlk 2000 karakteri alın
            String shortenedRegulation = regulation.substring(0, Math.min(regulation.length(), 1000)); // İlk 1000 karakteri alın

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-4-turbo");

            Map<String, String> messageContent = new HashMap<>();
            messageContent.put("role", "user");
            messageContent.put("content", "Site içeriği: " + shortenedSiteContent + " Düzenleme: " + shortenedRegulation);

            requestBody.put("messages", List.of(messageContent));
            requestBody.put("max_tokens", 500); // 1000'den daha az token
            requestBody.put("temperature", 0.7);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, entity, Map.class);

            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");

                if (!choices.isEmpty()) {
                    Map<String, Object> firstChoice = choices.get(0);
                    Map<String, String> message = (Map<String, String>) firstChoice.get("message");
                    return message.get("content");
                } else {
                    System.err.println("Choices listesi boş.");
                    return "API yanıtı beklenmedik formatta.";
                }
            } else {
                System.err.println("API yanıtı beklenmedik formatta veya 'choices' bulunamadı.");
                return "API yanıtı beklenmedik formatta.";
            }

        } catch (Exception e) {
            System.err.println("OpenAI Hatası: " + e.getMessage());
            return "GPT ile analiz sırasında hata oluştu: " + e.getMessage();
        }
    }

}
