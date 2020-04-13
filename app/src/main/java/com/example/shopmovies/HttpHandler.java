package com.example.shopmovies;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHandler {
    public static String makeServiceCall(String reqUrl) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(reqUrl).openConnection();
        conn.setRequestMethod("GET");
        return convertStreamToString(new BufferedInputStream(conn.getInputStream()));
    }

    private static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append('\n');
        }
        is.close();
        return sb.toString();
    }
}

