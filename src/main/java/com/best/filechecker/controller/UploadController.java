package com.best.filechecker.controller;

import com.best.filechecker.model.StoredFiles;
import com.best.filechecker.service.FileProcessor;
import com.best.filechecker.service.PdfValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

import static com.best.filechecker.util.mapping.FileCheckerMapping.*;
import static com.best.filechecker.util.mapping.FileNames.OUTPUT_FILE;
import static com.best.filechecker.util.mapping.ViewMapping.COMPARE_VIEW;
import static com.best.filechecker.util.mapping.ViewMapping.INDEX_VIEW;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UploadController {

    private final FileProcessor fileProcessor;
    private final PdfValidator pdfValidator;

    private StoredFiles storedFiles;

    @GetMapping(MAIN_PAGE)
    public String openUploadPage() {
        fileProcessor.clearFolder();
        return INDEX_VIEW;
    }

    @PostMapping(MAIN_PAGE)
    public String processUploadedFiles(@RequestParam("file") MultipartFile[] files, Model model){
        var outputFile = OUTPUT_FILE + Instant.now().toEpochMilli();
        storedFiles = fileProcessor.processUploadedFiles(files, outputFile);
        model.addAttribute("file1", storedFiles.getFirstFile());
        model.addAttribute("file2", storedFiles.getSecondFile());
        return COMPARE_VIEW;
    }

    @PostMapping(COMPARE_PAGE)
    public String generateOutput(){
        pdfValidator.compareFiles(storedFiles.getFirstFile(), storedFiles.getSecondFile(), storedFiles.getOutputFile());
        return "redirect:"+ DOWNLOAD_PAGE;
    }

    @GetMapping(DOWNLOAD_PAGE)
    public ResponseEntity<Resource> getOutputFile(){
        var path = Path.of(fileProcessor.getOutputFile(storedFiles.getOutputFile()));
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            log.error("Output file has not been found");
        }

        var header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + storedFiles.getOutputFile());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(path.toFile().length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
