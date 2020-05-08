package com.best.filechecker.service;

import de.redsix.pdfcompare.CompareResultWithMemoryOverflow;
import de.redsix.pdfcompare.PdfComparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.best.filechecker.util.mapping.FileNames.OUTPUT_FILE;

@Slf4j
@Service
public class PdfValidator {

    public void compareFiles(String firstFileLocation, String secondFileLocation) {
        System.getProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        try {
            new PdfComparator(firstFileLocation, secondFileLocation, new CompareResultWithMemoryOverflow())
                    .compare()
                    .writeTo(OUTPUT_FILE);
        } catch (IOException e) {
            log.error("error while reading a file");
            e.printStackTrace();
        }
    }
}
