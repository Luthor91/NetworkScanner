package com.networkscanner.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.networkscanner.model.ScanResult;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class JsonExporter {
    private static final ObjectMapper mapper = new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT)
        .findAndRegisterModules(); // Pour supporter LocalDateTime

    public static void exportToJson(List<ScanResult> results) throws Exception {
        // Créer le dossier Downloads s'il n'existe pas
        String downloadPath = getDownloadFolderPath();
        File downloadFolder = new File(downloadPath);
        if (!downloadFolder.exists()) {
            downloadFolder.mkdirs();
        }

        // Générer un nom de fichier avec timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = String.format("network_scan_%s.json", timestamp);
        File outputFile = new File(downloadFolder, fileName);

        // Écrire les résultats dans le fichier
        mapper.writeValue(outputFile, results);
    }

    private static String getDownloadFolderPath() {
        String userHome = System.getProperty("user.home");
        String os = System.getProperty("os.name").toLowerCase();
        
        if (os.contains("win")) {
            return Paths.get(userHome, "Downloads").toString();
        } else if (os.contains("mac")) {
            return Paths.get(userHome, "Downloads").toString();
        } else {
            // Linux/Unix
            return Paths.get(userHome, "Downloads").toString();
        }
    }

    public static String prettyPrintJson(Object obj) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error converting to JSON";
        }
    }
}
