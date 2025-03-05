package fr.eni.encheres.utils;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUploadUtil {

    private static final Path UPLOAD_DIR = Paths.get("upload-dir", "images");

    public static void ensureUploadDirExists() {
        if (Files.notExists(UPLOAD_DIR)) {
            try {
                Files.createDirectories(UPLOAD_DIR);
            } catch (Exception e) {
                // Gérer l'exception (par exemple, loguer l'erreur)
                e.printStackTrace();
            }
        }
    }

    public static String saveImage(MultipartFile file) {
        // Assurez-vous que le dossier upload-dir/images existe
        ensureUploadDirExists();
        try {
            // Générer un nom de fichier unique
            String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path targetLocation = UPLOAD_DIR.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), targetLocation);
            return "/images/" + uniqueFilename;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}