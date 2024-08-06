

/**
 * 
 * @author Sena Aksu sena.aksu1@ogr.sakarya.edu.tr
 * @since  07.04.2024
 * <p>
 * GitHub reposunu klonlamak ve Java dosyalarını analiz etmek amacıyla kullanılan sınıf.
 * </p>
 */


package b211210008pdp1;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//GitHubClone sınıfı, GitHub deposunu klonlamak ve Java dosyalarını analiz etmek için kullanılır.
public class GitHubClone {
    private static boolean klonlandi = false;  // Depo klonlama durumunu tutan değişke

    // Programın ana metodu
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("GitHub deposu URL'sini girin: ");
        String repositoryUrl = scanner.nextLine();
        scanner.close();

        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + File.separator + "Desktop" + File.separator + "MyRepo";

        // Önce mevcut klasörü kontrol et ve varsa sil
        // Bu sayede PROJE HER KLONLAMA YAPTIĞINDA yeniden klonlamak için dosayı silmemize gerek kalmayacak
        File existingRepo = new File(desktopPath);
        if (existingRepo.exists()) {
            deleteDirectory(existingRepo);
        }
     // Depoyu klonla ve varsa Java dosyalarını analiz et
        cloneRepository(repositoryUrl, desktopPath);

        if (klonlandi) {
            analyzeJavaFiles(desktopPath);
        }
    }
    // Depoyu klonlayan metod
    private static void cloneRepository(String repositoryUrl, String repositoryPath) {
        try {
            String command = "git clone " + repositoryUrl + " " + repositoryPath;
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                klonlandi = true;
                System.out.println("Depo başarıyla klonlandı.");
            } else {
                System.out.println("Depo klonlanırken hata oluştu.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Java dosyalarını analiz eden metod
    private static void analyzeJavaFiles(String directoryPath) {
        JavaFileFinder fileFinder = new JavaFileFinder();
        List<File> javaFiles = fileFinder.findJavaFiles(directoryPath);

        for (File javaFile : javaFiles) {
            JavaFileAnalyzer fileAnalyzer = new JavaFileAnalyzer(javaFile);
            fileAnalyzer.analyzeAndPrint();
        }
    }
    // Klasörü silen metod
    private static void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        directory.delete(); // IOException'u yakalamadan doğrudan silebiliriz
    }
}

