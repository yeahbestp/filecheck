package com.best.filechecker.service;

import com.best.filechecker.util.config.annotations.Location;
import de.redsix.pdfcompare.CompareResultWithMemoryOverflow;
import de.redsix.pdfcompare.PdfComparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class PdfValidator {

    public void compareFiles(String firstFile, String secondFile, String outputFile) {
        System.getProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        try {

            new PdfComparator(firstFile, secondFile, new CompareResultWithMemoryOverflow())
                    .compare()
                    .writeTo(outputFile);
        } catch (IOException e) {
            log.error("error while reading a file");
            e.printStackTrace();
        }
    }
}
