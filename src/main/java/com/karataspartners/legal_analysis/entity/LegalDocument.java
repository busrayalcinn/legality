package com.karataspartners.legal_analysis.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

// LegalDocument entity'si, web sitelerinin sahip olması gereken yasal belgeleri tutar.
@Entity
@Table(name = "legal_documents") // Bu entity, veritabanındaki "legal_documents" tablosuyla eşleşir.
@Getter
@Setter
@AllArgsConstructor // Tüm alanları içeren bir constructor (yapıcı metod) oluşturur.
@NoArgsConstructor // Parametresiz bir constructor oluşturur.
@Builder(toBuilder = true) // Nesne oluşturmayı kolaylaştıran Builder deseni kullanılır.
public class LegalDocument {

    // Bu alan, entity'nin birincil anahtarıdır ve otomatik olarak artar.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Yasal belgenin benzersiz kimliği (primary key)

    // Yasal belgenin adı (örneğin, Gizlilik Politikası, Kullanıcı Sözleşmesi)
    private String documentName; // Yasal belgenin adı (ne tür bir belge olduğunu belirtir)

    // Belgenin eksik mi, tamam mı olduğunu belirtir (true: tamam, false: eksik)
    private boolean isComplete; // Bu belge eksik mi yoksa tamam mı?

    // Bu belge hangi web sitesine ait olduğunu belirler (Website entity'si ile ilişki kurulur).
    // CascadeType.ALL ile bu belgeye ait web sitesi silindiğinde, LegalDocument de silinir.
    @ManyToOne(cascade = CascadeType.ALL) // Parent (Website) ile tüm işlemler uygulanır.
    @JoinColumn(name = "website_id") // "website_id" ile bu belge hangi siteye ait olduğunu belirtiyoruz.
    private Website website; // Bu belge hangi web sitesine ait olduğunu belirtir.

    // LegalDocument ve ScanResult arasındaki ManyToMany ilişkisi
    @ManyToMany(mappedBy = "missingDocuments")
    private List<ScanResult> scanResults; // Bu belge hangi taramalarda eksik bulundu, onu belirtir.
}
