

/**
 * 
 * @author Sena Aksu sena.aksu1@ogr.sakarya.edu.tr
 * @since  07.04.2024
 * <p>
 * Belirtilen dizindeki Java dosyalarını bulmak amacıyla kullanılan sınıf.
 * </p>
 */



package b211210008pdp1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JavaFileFinder {

	// Verilen dizin yolunda yer alan Java dosyalarını bulur.
    public List<File> findJavaFiles(String directoryPath) {
        List<File> javaFiles = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
        	 // Dizin mevcut ve bir dizin ise, Java dosyalarını aramaya başla
            findJavaFilesRecursively(directory, javaFiles);
        } else {
            System.out.println("Geçersiz dizin yolu.");
        }

        return javaFiles;
    }
   //  Verilen dizindeki Java dosyalarını rekürsif olarak bulur.
    //  directory Aranacak dizin
    // javaFiles Java dosyalarının listesi
    private void findJavaFilesRecursively(File directory, List<File> javaFiles) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                	// Eğer dosya bir dizin ise, içindeki Java dosyalarını da ara
                    findJavaFilesRecursively(file, javaFiles);
                } else if (file.getName().endsWith(".java")) {
                	 // Dosya .java uzantılı ise, Java dosyası olarak kabul edip listeye ekle
                    javaFiles.add(file);
                }
            }
        }
    }
}
