package com.best.filechecker.validation;

import com.best.filechecker.util.exceptions.StorageException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileValidators {

    private static final String PATTERN = "\\.";

    public void validateUploadedFiles(MultipartFile[] files) {
        nonExitingFileValidator(files);
        fileFormatValidator(files);
        differentFileValidator(files);
    }

    private void nonExitingFileValidator(MultipartFile[] files) {
        if (files[0].isEmpty()) {
            throw new StorageException("Failed to store empty file, first file is empty");
        } else if (files[1].isEmpty()) {
            throw new StorageException("Failed to store empty file, Second file is empty");
        }
    }

    private void fileFormatValidator(MultipartFile[] files) {
        var firstFile = getFileExtensions(files[0].getOriginalFilename());
        var secondFile = getFileExtensions(files[1].getOriginalFilename());
        if (!firstFile.equalsIgnoreCase(secondFile)) {
            throw new StorageException("File formats are not the same");
        }
    }

    private void differentFileValidator(MultipartFile[] files) {
        var fileNames = getFileNames(files);
        if (fileNames.get(0).equalsIgnoreCase(fileNames.get(1))) {
            throw new StorageException("Files are the same");
        }
    }

    private String getFileExtensions(String file) {
        return file.split(PATTERN)[1];
    }

    private List<String> getFileNames(MultipartFile[] files) {
        return Arrays.stream(files)
                .map(MultipartFile::getOriginalFilename)
                .collect(Collectors.toList());
    }
}
