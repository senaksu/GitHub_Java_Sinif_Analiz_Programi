# GitHub Java Sınıf Analiz Programı

Bu proje, kullanıcıdan verilen GitHub depo bağlantısını kullanarak bir Java projesini klonlar ve içerisindeki `.java` uzantılı dosyaları analiz eder. Analiz sonuçları, her bir Java sınıfı için aşağıdaki metrikleri içerir:

- Javadoc yorum satır sayısı
- Diğer yorum satır sayısı
- Kod satır sayısı (yorum ve boşluk satırları hariç)
- LOC (Line of Code) (bir dosyadaki tüm satır sayısı)
- Fonksiyon sayısı (sınıf içindeki toplam fonksiyon sayısı)
- Yorum sapma yüzdesi (yazılması gereken yorum satır sayısına göre sapma)

## Proje İçeriği

### `JavaFileAnalyzer.java`
Bu sınıf, verilen Java dosyasını analiz eder ve çeşitli metrikleri hesaplar:
- **Javadoc Satır Sayısı**: Java dosyasındaki Javadoc yorum satırlarının sayısını belirler.
- **Diğer Yorum Satır Sayısı**: Java dosyasındaki diğer yorum satırlarının sayısını hesaplar.
- **Kod Satır Sayısı**: Java dosyasındaki kod satırlarının sayısını belirler (yorum ve boşluk satırları hariç).
- **LOC (Line of Code)**: Java dosyasındaki toplam satır sayısını hesaplar.
- **Fonksiyon Sayısı**: Java dosyasındaki fonksiyonların toplam sayısını hesaplar.
- **Yorum Sapma Yüzdesi**: Yazılması gereken yorum satır sayısına göre sapma yüzdesi.

### `JavaFileFinder.java`
Bu sınıf, belirtilen dizindeki tüm Java dosyalarını bulur ve yalnızca sınıf içeren dosyaları ayıklar.

### `GitHubClone.java`
Bu sınıf, kullanıcıdan alınan GitHub deposu URL'sine göre bu depoyu klonlar ve klonlanan depodaki Java dosyalarını analiz eder. Analiz sonuçları ekrana yazdırılır.

## Kullanım

1. **Proje İndirme ve Derleme**

   - GitHub deposunun URL'sini girdikten sonra, proje otomatik olarak bu depoyu klonlayacak ve Java dosyalarını analiz edecektir.
   - Proje, Eclipse ortamında çalıştırılmak üzere hazırlanmıştır.

2. **Programı Çalıştırma**

   - GitHub deposu URL'sini girin ve analiz sonuçlarını görüntüleyin.

 ## Dosya Yapısı

- **`JavaFileAnalyzer.java`**: Java dosyalarını analiz eden sınıf.
- **`JavaFileFinder.java`**: Java dosyalarını belirli bir dizinde arayan sınıf.
- **`GitHubClone.java`**: GitHub deposunu klonlayan ve Java dosyalarını analiz eden sınıf.

## Gereksinimler

- Eclipse IDE
- Java Development Kit (JDK) 8 veya daha üstü
- Git (depo klonlama için)

## Kurulum ve Çalıştırma

1. Projeyi Eclipse ortamına import edin.
2. `GitHubClone` sınıfını çalıştırarak GitHub deposunu klonlayın.
3. Java dosyalarını analiz edin ve sonuçları görüntüleyin.
