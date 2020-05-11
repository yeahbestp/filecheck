package com.best.filechecker.validation;

import com.best.filechecker.util.exceptions.StorageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class FileValidatorsTest {
    private static final byte[] CONTENT = {1,2,3};
    private static final byte[] EMPTY_CONTENT = null;
    private final static String CONTENT_TYPE = null;
    private final static String FILE = "file";

    @InjectMocks
    private FileValidators fileValidators;

    @Test
    void shouldValidateAllFiles() {
        // given
        MockMultipartFile firstFile = new MockMultipartFile(FILE,"file1.pdf",CONTENT_TYPE, CONTENT);
        MockMultipartFile secondFile = new MockMultipartFile(FILE,"file2.pdf",CONTENT_TYPE, CONTENT);
        MultipartFile [] files = {firstFile, secondFile};

        // then
        assertAll(() ->fileValidators.validateUploadedFiles(files));
    }

    @Test
    void shouldThrowExceptionForFirstEmptyFile() {
        // given
        MockMultipartFile firstFile = new MockMultipartFile(FILE,"file1.pdf",CONTENT_TYPE, EMPTY_CONTENT);
        MockMultipartFile secondFile = new MockMultipartFile(FILE,"file2.pdf",CONTENT_TYPE, CONTENT);
        MultipartFile [] files = {firstFile, secondFile};

        // then
        assertThrows(StorageException.class,
                () -> fileValidators.validateUploadedFiles(files));
    }

    @Test
    void shouldThrowExceptionForSecondEmptyFile() {
        // given
        MockMultipartFile firstFile = new MockMultipartFile(FILE,"file1.pdf",CONTENT_TYPE, CONTENT);
        MockMultipartFile secondFile = new MockMultipartFile(FILE,"file2.pdf",CONTENT_TYPE, EMPTY_CONTENT);
        MultipartFile [] files = {firstFile, secondFile};

        // then
        assertThrows(StorageException.class,
                () -> fileValidators.validateUploadedFiles(files));
    }

    @Test
    void shouldThrowExceptionWhenFileFormatDiffers() {
        // given
        MockMultipartFile firstFile = new MockMultipartFile(FILE,"file1.xlsx",CONTENT_TYPE, CONTENT);
        MockMultipartFile secondFile = new MockMultipartFile(FILE,"file2.pdf",CONTENT_TYPE, CONTENT);
        MultipartFile [] files = {firstFile, secondFile};

        // then
        assertThrows(StorageException.class,
                () -> fileValidators.validateUploadedFiles(files));
    }

    @Test
    void shouldThrowExceptionWhenFilesHaveTheSameName() {
        MockMultipartFile firstFile = new MockMultipartFile(FILE,"file1.pdf",CONTENT_TYPE, CONTENT);
        MockMultipartFile secondFile = new MockMultipartFile(FILE,"file1.pdf",CONTENT_TYPE, CONTENT);
        MultipartFile [] files = {firstFile, secondFile};

        // then
        assertThrows(StorageException.class,
                () -> fileValidators.validateUploadedFiles(files));
    }
}