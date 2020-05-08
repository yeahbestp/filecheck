package com.best.filechecker.service;

import com.best.filechecker.model.StoredFiles;
import com.best.filechecker.util.config.annotations.Location;
import com.best.filechecker.util.exceptions.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.best.filechecker.util.mapping.FileNames.OUTPUT_FILE;
import static com.best.filechecker.util.wrappers.ConsumerWrapper.consumerWrapper;

@Slf4j
@Service
public class FileProcessor {
    private final Path path;
    public FileProcessor(@Location String location) {
        this.path = Paths.get(location);
    }

    public void storeFiles(MultipartFile[] files) {
        if (files[0].isEmpty()) {
            throw new StorageException("Failed to store empty file, first file is empty");
        } else if (files[1].isEmpty()) {
            throw new StorageException("Failed to store empty file, Second file is empty");
        }

        Arrays.stream(files)
                .forEach(file -> {
                    try {
                        Files.copy(file.getInputStream(),
                                this.path.resolve(Objects.requireNonNull(file.getOriginalFilename())));
                    } catch (IOException e) {
                        log.error("Error while searching uploaded files");
                    }
                });
    }

    public StoredFiles processStoredFiles(MultipartFile[] files){
        var submittedFiles = getSubmittedFiles();
        return StoredFiles.builder()
                .firstFile(submittedFiles.get(0))
                .secondFile(submittedFiles.get(1))
                .outputFile(OUTPUT_FILE + Instant.now().toEpochMilli())
                .build();
    }

    public String getOutputFile() {
        try {
            return Files.walk(path)
                    .filter(s -> !Files.isDirectory(s))
                    .filter(s -> s.getFileName().toString().contains(OUTPUT_FILE))
                    .map(Path::toString)
                    .findAny()
                    .orElseThrow(() -> new StorageException("File has not been generated"));
        } catch (IOException e) {
            throw new StorageException("OutputFile files not found", e.getCause());
        }
    }

    public void clearFolder() {
        try {
            Files.walk(path)
                    .filter(s -> !Files.isDirectory(s))
                    .forEach(consumerWrapper(Files::delete));
        } catch (IOException e) {
            log.error("folder has not been cleared");
        }
    }

    private List<String> getSubmittedFiles() {
        try {
            return Files.walk(path)
                    .filter(s -> !Files.isDirectory(s))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Submitted files not found", e.getCause());
        }
    }
}
