package com.best.filechecker.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StoredFiles {
    private String firstFileLocation;
    private String secondFileLocation;
    private String outputFileLocation;
    private String firstFileName;
    private String secondFileName;
    private String outputFileName;
}
