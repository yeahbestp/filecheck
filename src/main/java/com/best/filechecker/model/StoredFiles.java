package com.best.filechecker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StoredFiles {
    @NotEmpty(message = "Failed to store empty file, first file is empty")
    private String firstFile;
    @NotEmpty(message = "Failed to store empty file, second file is empty")
    private String secondFile;
    private String outputFile;
}
