package com.compair.File.controller;



import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/compare")
public class controller {

    @PostMapping
    public ResponseEntity<Map<String, String>> compareDocuments(
            @RequestParam("file1") MultipartFile file1,
            @RequestParam("file2") MultipartFile file2) {

        try {
            String text1 = new String(file1.getBytes(), StandardCharsets.UTF_8);
            String text2 = new String(file2.getBytes(), StandardCharsets.UTF_8);

            List<String> lines1 = text1.lines().collect(Collectors.toList());
            List<String> lines2 = text2.lines().collect(Collectors.toList());

            StringBuilder differences = new StringBuilder();
            int maxLines = Math.max(lines1.size(), lines2.size());

            for (int i = 0; i < maxLines; i++) {
                String line1 = i < lines1.size() ? lines1.get(i) : "(no content)";
                String line2 = i < lines2.size() ? lines2.get(i) : "(no content)";

                if (!line1.equals(line2)) {
                    differences.append("Line ").append(i + 1).append(":\n");
                    differences.append("File 1: ").append(line1).append("\n");
                    differences.append("File 2: ").append(line2).append("\n\n");
                }
            }

            if (differences.length() == 0) {
                differences.append("The files are identical.");
            }

            // Return a JSON object
            Map<String, String> result = new HashMap<>();
            result.put("comparisonResult", differences.toString());

            return ResponseEntity.ok(result);

        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error reading files: " + e.getMessage()));
        }
    }
}