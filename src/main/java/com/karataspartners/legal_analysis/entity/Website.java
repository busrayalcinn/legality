package com.karataspartners.legal_analysis.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

// Website entity'si, web sitelerine ait temel bilgileri tutar.
@Entity
@Table(name = "websites") // Bu entity, veritabanındaki "websites" tablosuyla eşleşir.
@Getter
@Setter
@AllArgsConstructor // Tüm alanları içeren bir constructor (yapıcı metod) oluşturur.
@NoArgsConstructor // Parametresiz bir constructor oluşturur.
@Builder(toBuilder = true) // Nesne oluşturmayı kolaylaştıran Builder deseni kullanılır.
public class Website {

    // Bu alan, entity'nin birincil anahtarıdır ve otomatik olarak artar.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Web sitesinin benzersiz kimliği (primary key)

    // Web sitesinin URL adresini tutar.
    private String url; // Web sitesinin URL'si (örneğin, https://www.example.com)

    // Web sitesinin sahibinin adını tutar.
    private String ownerName; // Web sitesinin sahibi (şirket veya kişi)

    // Bir web sitesi ile ilişkili birden fazla yasal belge olabilir. Bu ilişki, LegalDocument entity'siyle kurulur.
    // CascadeType.ALL kullanımı, Website silindiğinde ona bağlı LegalDocument'lerin de silinmesini sağlar.
    @OneToMany(mappedBy = "website", cascade = CascadeType.ALL) // Tüm işlemler (persist, merge, remove) parent-child arasında uygulanır.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // API üzerinden sadece yazılabilir, okunmaz.
    private List<LegalDocument> legalDocuments; // Web sitesiyle ilişkili yasal belgelerin listesi
}
