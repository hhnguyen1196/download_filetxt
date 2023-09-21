package com.jackson.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jackson.service.JobOfferDownloadService;

import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
public class ApiDownloadController {

    private final JobOfferDownloadService jobOfferDownloadService;

    @GetMapping(value = "/api/download", produces = "text/plain")
    public ResponseEntity<Resource> index() throws IOException {
        return jobOfferDownloadService.execute();
    }

}