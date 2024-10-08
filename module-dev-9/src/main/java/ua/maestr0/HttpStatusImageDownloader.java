package ua.maestr0;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ua.maestr0.HttpStatusChecker.getStatusImage;

public class HttpStatusImageDownloader {

    private static final String DEFAULT_DOWNLOAD_DIR = "downloaded-images";

    public void downloadStatusImage(int code) throws Exception {
        String imageUrl = getStatusImage(code);
        Path downloadDir = createDownloadDirectory(DEFAULT_DOWNLOAD_DIR);
        String fileName = code + ".jpg";
        Path filePath = downloadDir.resolve(fileName);

        downloadImage(imageUrl, filePath);
        System.out.println("Зображення успішно завантажено: " + filePath.toString());
    }

    private Path createDownloadDirectory(String dirName) throws IOException {
        Path path = Paths.get(dirName);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        return path;
    }

    private void downloadImage(String imageUrl, Path filePath) throws Exception {
        try (BufferedInputStream in = new BufferedInputStream(new URL(imageUrl).openStream());
             FileOutputStream out = new FileOutputStream(filePath.toFile())) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new Exception("Помилка під час завантаження зображення: " + e.getMessage(), e);
        }
    }
}
