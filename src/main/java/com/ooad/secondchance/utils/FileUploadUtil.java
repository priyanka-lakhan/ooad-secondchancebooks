package com.ooad.secondchance.utils;

/**
 * Created by Priyanka on 3/20/21.
 */
import java.io.*;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        String resourcesPath = FileUploadUtil.class.getClassLoader().getResource("webapp").getPath();
        uploadDir = resourcesPath + "/" + uploadDir;
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}
