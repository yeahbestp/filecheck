package com.best.filechecker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FileProcessorTest {

    @Mock
    private FileProcessor fileProcessor;

    private MultipartFile[] file;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.file = new MultipartFile[2];
    }

    @Test
    void shouldThrowException() {
        //Given
        doThrow(IllegalArgumentException.class).when(fileProcessor).storeFiles(any());

        //Then
        assertThrows(IllegalArgumentException.class, () -> fileProcessor.storeFiles(file));
    }

    @Test
    void shouldStoreFiles() {
        //Given
        doNothing().when(fileProcessor).storeFiles(any());

        //When
        fileProcessor.storeFiles(file);

        //Then
        verify(fileProcessor, times(1)).storeFiles(file);
    }

    @Test
    void shouldClearFolderThrowException() {
        //Given
        doThrow(NullPointerException.class).when(fileProcessor).clearFolder();

        //Then
        assertThrows(NullPointerException.class, () -> fileProcessor.clearFolder());
    }

}