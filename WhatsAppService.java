package com.bloodbridge.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class WhatsAppService {

    @Value("${whatsapp.access-token:}")
    private String accessToken;

    @Value("${whatsapp.phone-number-id:}")
    private String phoneNumberId;

    @Value("${whatsapp.api-version:v23.0}")
    private String apiVersion;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String sendTextMessage(
            String recipientPhone,
            String message) throws Exception {

        if (accessToken == null || accessToken.isBlank()
                || phoneNumberId == null || phoneNumberId.isBlank()) {

            throw new IllegalStateException(
                    "WhatsApp credentials are not configured.");
        }

        String endpoint =
                "https://graph.facebook.com/"
                + apiVersion + "/"
                + phoneNumberId + "/messages";

        Map<String, Object> payload = Map.of(
                "messaging_product", "whatsapp",
                "recipient_type", "individual",
                "to", recipientPhone,
                "type", "text",
                "text", Map.of(
                        "preview_url", false,
                        "body", message
                )
        );

        String json = objectMapper.writeValueAsString(payload);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + accessToken)
                .header(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() < 200
                || response.statusCode() >= 300) {

            throw new IllegalStateException(
                    "WhatsApp API error. HTTP status: "
                    + response.statusCode()
                    + ". Response: "
                    + response.body());
        }

        return response.body();
    }
}