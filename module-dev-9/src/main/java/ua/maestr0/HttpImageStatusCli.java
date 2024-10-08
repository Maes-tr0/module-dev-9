package ua.maestr0;

import java.util.Scanner;

public class HttpImageStatusCli {

    private final HttpStatusImageDownloader downloader;

    public HttpImageStatusCli() {
        downloader = new HttpStatusImageDownloader();
    }

    public void askStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter HTTP status code:");

        String input = scanner.nextLine();
        int statusCode;

        // Перевіряємо, чи введено валідне число
        try {
            statusCode = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid number");
            return;
        }

        // Працюємо з завантаженням зображення через HttpStatusImageDownloader
        try {
            downloader.downloadStatusImage(statusCode);
        } catch (Exception e) {
            System.out.println("There is not image for HTTP status " + statusCode);
        }
    }

    public static void main(String[] args) {
        HttpImageStatusCli cli = new HttpImageStatusCli();
        cli.askStatus();
    }
}
