package com.karataspartners.legal_analysis.service;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

@Service
public class WebScraperService {

    public String getContentFromUrl(String url) {
        try {
            // Jsoup ile web sayfasının HTML içeriğini alıyoruz
            return Jsoup.connect(url).get().text(); // Sayfanın metin içeriğini döndür
        } catch (Exception e) {
            System.err.println("Error fetching content from URL: " + e.getMessage());
            return "Error fetching content.";
        }
    }
}
