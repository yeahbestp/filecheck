package com.best.filechecker.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StoredFiles {
    private String firstFile;
    private String secondFile;
    private String outputFile;
}
