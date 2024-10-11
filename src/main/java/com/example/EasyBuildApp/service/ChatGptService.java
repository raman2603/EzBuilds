package com.example.EasyBuildApp.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatGptService {

    // The ChatGPT API endpoint
    private static final String CHATGPT_API_URL = "https://api.openai.com/v1/chat/completions";

    // Replace with your actual API key
    private final String API_KEY = "YOUR_API_KEY";

    public String getYamlScript(String question) {
        RestTemplate restTemplate = new RestTemplate();

        // Create the request body
        Map<String, Object> request = new HashMap<>();
        request.put("model", "gpt-3.5-turbo");
        request.put("messages", List.of(Map.of("role", "user", "content", question)));

        // Set the headers including the authorization
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        // Create an HttpEntity containing the request body and headers
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        // Send the request and get the response
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                CHATGPT_API_URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        // Parse the response to extract the YAML script
        JSONObject jsonResponse = new JSONObject(responseEntity.getBody());
        String yamlScript = jsonResponse.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

        return yamlScript; // Return the extracted YAML script
    }
}
