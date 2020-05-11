package com.best.filechecker.service;

import com.best.filechecker.model.StoredFiles;
import com.best.filechecker.util.config.annotations.Location;
import com.best.filechecker.util.exceptions.StorageException;
import com.best.filechecker.validation.FileValidators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.best.filechecker.util.wrappers.ConsumerWrapper.consumerWrapper;

@Slf4j
@Service
public class FileProcessor {
    private final Path path;

    public FileProcessor(@Location String location) {
        this.path = Paths.get(location);
    }

    public StoredFiles processUploadedFiles(MultipartFile[] files, String outputFile) {
        var fileValidator = new FileValidators();
        fileValidator.validateUploadedFiles(files);
        storeFiles(files);
        var submittedFiles = getSubmittedFiles();
        return StoredFiles.builder()
                .firstFileLocation(submittedFiles.get(0))
                .secondFileLocation(submittedFiles.get(1))
                .outputFileLocation(path.resolve(outputFile).toString())
                .firstFileName(files[0].getOriginalFilename())
                .secondFileName(files[1].getOriginalFilename())
                .outputFileName(outputFile)
                .build();
    }

    private void storeFiles(MultipartFile[] files) {
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

    public String getOutputFile(String outputFile) {
        try {
            return Files.walk(path)
                    .filter(s -> !Files.isDirectory(s))
                    .filter(s -> s.toString().contains(outputFile))
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
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Submitted files not found", e.getCause());
        }
    }
}
