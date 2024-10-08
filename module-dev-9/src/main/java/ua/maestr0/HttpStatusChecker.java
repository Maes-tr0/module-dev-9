package ua.maestr0;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class HttpStatusChecker {
    private static final String TEMPLATE_TO_GET_CODE = "https://http.cat/%s";

    private HttpStatusChecker(){}
    public static String getStatusImage(int code) {
        if (!isCodeStatusValid(code)) {
            throw new RuntimeException();
        }
        return TEMPLATE_TO_GET_CODE.formatted(code) + ".jpg";
    }

    private static boolean isCodeStatusValid(int code) {
        try {
            URI uri = URI.create(String.format(TEMPLATE_TO_GET_CODE, code));
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            return connection.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
