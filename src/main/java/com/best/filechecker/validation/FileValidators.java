package com.best.filechecker.validation;

import com.best.filechecker.util.exceptions.StorageException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileValidators {

    private static List<String> getFileNames(MultipartFile[] files) {
        return Arrays.stream(files).map(MultipartFile::getName)
                .collect(Collectors.toUnmodifiableList());
    }

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
        var fileFormats = getFileNames(files).stream()
                .map(s -> s.split(",")[1])
                .collect(Collectors.toList());

        if (!fileFormats.get(0).equalsIgnoreCase(fileFormats.get(1))) {
            throw new StorageException("File formats are not the same");
        }
    }

    private void differentFileValidator(MultipartFile[] files) {
        var fileNames = getFileNames(files);
        if (fileNames.get(0).equalsIgnoreCase(fileNames.get(1))) {
            throw new StorageException("Files are the same");
        }
    }
}
