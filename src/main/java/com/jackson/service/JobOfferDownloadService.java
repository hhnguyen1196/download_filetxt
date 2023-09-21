package com.jackson.service;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.jackson.entity.JobOffersApiDownloadGetApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class JobOfferDownloadService {

    private final ObjectMapper objectMapper;
    private static final String TAB = "\t";

    public ResponseEntity<Resource> execute() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String fileName = "fileLocal".concat(dtf.format(LocalDateTime.now()));
        File file;

        try {
            file = File.createTempFile(fileName, ".txt");
        } catch (IOException ex) {
            throw new IOException();
        }

        try (FileWriter fw = new FileWriter(file, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            JavaType javaType = objectMapper.getSerializationConfig().constructType(JobOffersApiDownloadGetApiResponse.class);
            BeanDescription beanDescription = objectMapper.getSerializationConfig().introspect(javaType);
            List<BeanPropertyDefinition> beanDescriptionList = beanDescription.findProperties();
            String header = beanDescriptionList.stream().map(BeanPropertyDefinition::getName).collect(Collectors.joining(TAB));
            pw.println(header);
            pw.print("001\tKarina\tae");
            pw.flush();
            file.deleteOnExit();
        } catch (IOException ex) {
            throw new IOException();
        }
        String responseFilename = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8);
        String pathFile = "file:///C:/Users/Admin/AppData/Local/Temp/" + responseFilename;

        Resource resource = new UrlResource(pathFile);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+ responseFilename)
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }
}