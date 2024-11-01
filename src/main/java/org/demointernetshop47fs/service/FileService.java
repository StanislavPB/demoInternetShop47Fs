package org.demointernetshop47fs.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.demointernetshop47fs.dto.ErrorResponseDto;
import org.demointernetshop47fs.dto.StandardResponseDto;
import org.demointernetshop47fs.entity.FileInfo;
import org.demointernetshop47fs.repository.FileInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileInfoRepository repository;

    private final Path fileStorageLocation = Paths.get("src/main/resources/static/upload");

    @Transactional
    @SneakyThrows
    public StandardResponseDto upload(MultipartFile file) {

        String originalFileName = file.getOriginalFilename(); // получаем исходное имя фала

        String extension = "";

        if (originalFileName != null) {
            int indexExtension = originalFileName.lastIndexOf(".") + 1; // получаем индекс начала расширения файла
            extension = originalFileName.substring(indexExtension);
        } else {
            throw new NullPointerException("null original file name");
        }

        String uuid = UUID.randomUUID().toString(); // генерация случайной строки в формате UUID
        String newFileName = uuid + "." + extension; // создание нового имени файла

        //

        Path targetLocation = fileStorageLocation.resolve(newFileName);

        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        String link = targetLocation.toString();
        FileInfo fileInfo = new FileInfo();
        fileInfo.setLink(link);
        repository.save(fileInfo);


        return new StandardResponseDto("Файл " + link + " успешно сохранен");


    }
}
