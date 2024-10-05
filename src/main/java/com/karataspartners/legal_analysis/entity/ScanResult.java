package com.karataspartners.legal_analysis.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

// ScanResult entity'si, web sitesi tarama sonuçlarını tutar (eksik belgeler ve tarama tarihi gibi).
@Entity
@Table(name = "scan_results") // Bu entity, veritabanındaki "scan_results" tablosuyla eşleşir.
@Getter
@Setter
@AllArgsConstructor // Tüm alanları içeren bir constructor (yapıcı metod) oluşturur.
@NoArgsConstructor // Parametresiz bir constructor oluşturur.
@Builder(toBuilder = true) // Nesne oluşturmayı kolaylaştıran Builder deseni kullanılır.
public class ScanResult {

    // Bu alan, entity'nin birincil anahtarıdır ve otomatik olarak artar.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Tarama sonucunun benzersiz kimliği (primary key)

    // Bu taramanın hangi web sitesine ait olduğunu belirtir (Website entity'si ile ilişki kurulur).
    // CascadeType.ALL ile Website silindiğinde bu tarama sonucu da silinir.
    @ManyToOne(cascade = CascadeType.ALL) // Parent (Website) ile tüm işlemler uygulanır.
    @JoinColumn(name = "website_id") // "website_id" ile bu tarama sonucunun hangi siteye ait olduğunu belirtiriz.
    private Website website; // Tarama yapılan web sitesi

    // LegalDocument ile ManyToMany ilişki, eksik belgeleri tutmak için kullanılıyor
    @ManyToMany
    @JoinTable(
            name = "scan_missing_documents", // Ortak tablo adı
            joinColumns = @JoinColumn(name = "scan_id"), // ScanResult ID'si
            inverseJoinColumns = @JoinColumn(name = "document_id") // LegalDocument ID'si
    )
    private List<LegalDocument> missingDocuments; // Eksik olan belgelerin listesi (LegalDocument ile ilişki)

    // Taramanın yapıldığı tarih ve saat bilgisi
    private LocalDateTime scanDate; // Tarama tarihi ve saati
}
