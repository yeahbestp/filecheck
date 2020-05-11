package com.best.filechecker.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FileFormat {
    PDF(".pdf");

    public final String EXTENSION;
}
