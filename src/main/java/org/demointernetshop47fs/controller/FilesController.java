package org.demointernetshop47fs.controller;

import lombok.RequiredArgsConstructor;
import org.demointernetshop47fs.dto.StandardResponseDto;
import org.demointernetshop47fs.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FilesController {

    private final FileService service;

    @PostMapping("/api/files")
    public StandardResponseDto upload(@RequestParam("file")MultipartFile file){
        return service.upload(file);
    }
}
