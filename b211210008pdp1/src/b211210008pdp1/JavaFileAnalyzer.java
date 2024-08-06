

/**
 * 
 * @author Sena Aksu sena.aksu1@ogr.sakarya.edu.tr
 * @since  07.04.2024
 * <p>
 * Klonlanan  Java dosyasını analiz ederek çeşitli metrikleri hesaplayan ve sonuçları yazdıran sınıf.
 * </p>
 */


package b211210008pdp1;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.classfile.instruction.ReturnInstruction;
import java.time.LocalDate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DecimalFormat;

public class JavaFileAnalyzer {
    private File javaFile;    // Analiz edilecek Java dosyasını temsil eden değişken

    public JavaFileAnalyzer(File javaFile) {
        this.javaFile = javaFile;
    }
    
    /**
     * Java dosyasını analiz eder ve sonuçları ekrana yazdırır.
     */
    public void analyzeAndPrint() {
    	// Java dosyası geçerli mi, var mı ve bir dosya mı diye kontrol et
        if (javaFile != null && javaFile.exists() && javaFile.isFile()) {
        	 // Dosya içinde bir sınıf var mı diye kontrol et
        	if (containsClass()) {
                int javadocSatirlari = javadocSatirSayisiHesapla();
                int YorumSatirlari = YorumSatirSayisiHesapla();
                int kodSatirlari = kodSatirSayisiHesapla();
                int LineOfCodeSatirlari= LocSatirSayisiHesapla();
                int FonksiyonSayisi = FonksiyonSayisiHesapla();
             
             double yorumSapmaYuzdesi = YorumSapmaHesaplama();

                System.out.println("Sınıf: " + javaFile.getName());
                System.out.println("Javadoc Satır Sayısı: " + javadocSatirlari);
                System.out.println("Yorum Satır Sayısı: " + YorumSatirlari);
                System.out.println("Kod Satır Sayısı: " + kodSatirlari);
                System.out.println("LOC: " + LineOfCodeSatirlari);
                System.out.println("Fonksiyon Sayısı: " +FonksiyonSayisi );
                System.out.println("Yorum Sapma Yüzdesi: %" + String.format("%.2f", yorumSapmaYuzdesi));
                System.out.println("-----------------------------------------");
            } else {
            	// Dosya içinde sınıf bulunamadığında
             //   System.out.println("Geçersiz veya sınıf içermeyen dosya: " + javaFile.getName());
            }
        } else {
        	 // Dosya geçerli değilse
           // System.out.println("Geçersiz dosya: " + javaFile.getName());
        }
    }

    /**
     * Java dosyasının içinde bir sınıf tanımı bulunup bulunmadığını kontrol eder.
     * @return Eğer sınıf tanımı bulunursa true, aksi halde false döner.
     */
    private boolean containsClass() {
        boolean isClassFound = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(javaFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	 // Dosyanın her satırını oku
                // Eğer satır içinde "class " kelimesi geçiyorsa, bir sınıf tanımı bulunmuş demektir
                if (line.contains("class ")) {
                    isClassFound = true; // Sınıf bulunduğunda değişkeni true yap ve döngüden çık
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isClassFound;
    }

    
 // javadocSatirSayisiHesapla: Dosyadaki Javadoc satır sayısını hesaplar.
    private int javadocSatirSayisiHesapla() {
        int cokluYorumSatirSayi = 0;
        int javadocSatirlari=0;
        int yorumBasBitisSayisi=0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(javaFile))) {
            String satir;
            boolean cokluYorumIcınde = false;

            while ((satir = reader.readLine()) != null) {
                satir = satir.trim();
                if (satir.startsWith("/**")) {
                	cokluYorumIcınde = true;
                	  yorumBasBitisSayisi++;
                }

                if (cokluYorumIcınde) {
                	cokluYorumSatirSayi++;
                }

                if (satir.endsWith("*/")) {
                	cokluYorumIcınde = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        javadocSatirlari= cokluYorumSatirSayi-2*yorumBasBitisSayisi;
        return javadocSatirlari;
    }
    

    // YorumSatirSayisiHesapla: Dosyadaki toplam yorum satır sayısını hesaplar.
    private int YorumSatirSayisiHesapla() {
        int YorumSatirlari = 0;
        boolean inMultiLineComment = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(javaFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("/**")) { // Javadoc başlangıcı kontrolü
                    inMultiLineComment = true;
                }

                if (inMultiLineComment) {
                    if (line.contains("*/")) { // Javadoc bitişi kontrolü
                        inMultiLineComment = false;
                    }
                    continue; // Javadoc içindeki yorumları sayma
                }

                if (line.startsWith("/*")) { // Çok satırlı yorum başlangıcı kontrolü
                    inMultiLineComment = true;
                    if (!line.contains("*/")) { // Çok satırlı yorumun bitişinin aynı satırda olup olmadığını kontrol et
                    	YorumSatirlari++;
                    }
                    continue;
                }

                if (line.contains("//")) { // Tek satırlı yorum kontrolü
                	YorumSatirlari++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return YorumSatirlari;
    }

    
    
 // kodSatirSayisiHesapla: Dosyadaki kod satırı sayısını hesaplar.
    private int kodSatirSayisiHesapla() {

        int kodSatirlari = 0;
        boolean inMultiLineComment = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(javaFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (inMultiLineComment) {
                    if (line.contains("*/")) {
                        inMultiLineComment = false;
                        line = line.substring(line.indexOf("*/") + 2).trim();
                    } else {
                        continue;
                    }
                }

                if (line.startsWith("/*")) {
                    inMultiLineComment = true;
                    if (line.contains("*/")) {
                        inMultiLineComment = false;
                        line = line.substring(line.indexOf("*/") + 2).trim();
                    } else {
                        continue;
                    }
                }

                if (!inMultiLineComment && !line.isEmpty() && !line.startsWith("//")) {
                    kodSatirlari++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kodSatirlari;
    }
    //LocSatirSayisiHesapla() : Dosyadaki toplam LOC satır sayısını hesaplar.
    private int LocSatirSayisiHesapla() {
        int LineOfCodeSatirlari = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(javaFile))) {
            while (reader.readLine() != null) {
            	LineOfCodeSatirlari++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return LineOfCodeSatirlari;
    }
    // FonksiyonSayisiHesapla(): Dosyadaki Fonksiyon sayisini  hesaplar.
    private int FonksiyonSayisiHesapla() {
    	 int functionCount = 0;
    	   

    	    try (BufferedReader reader = new BufferedReader(new FileReader(javaFile))) {
    	    	
    	             String line;
    	             while ((line = reader.readLine()) != null) {
    	                 line = line.trim();
    	                 if (((line.startsWith("public") || line.startsWith("private") || line.startsWith("protected")) ||
    	                         line.contains("interface")) && line.contains("(") && line.contains(")") && line.contains("{")) {
    	                     functionCount++;
    	                 }
    	             }
    	         } catch (Exception e) {
    	             e.printStackTrace();
    	         }

    	         return functionCount;
      
    }
    // YorumSapmaHesaplama(): Dosyadaki Yorum sapma yüzdesini hesaplar.
  private double YorumSapmaHesaplama() {
    	   double YG = ((javadocSatirSayisiHesapla() + YorumSatirSayisiHesapla()) * 0.8) / FonksiyonSayisiHesapla();
           double YH = ((double) kodSatirSayisiHesapla() / FonksiyonSayisiHesapla()) * 0.3;

        return ((100 * YG) / YH) - 100;
    }
}
    	
    	
    
    
    
    
    
    
    




