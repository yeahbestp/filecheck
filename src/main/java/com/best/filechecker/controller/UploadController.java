package com.best.filechecker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.best.filechecker.util.mapping.FileCheckerMapping.MAIN_PAGE;
import static com.best.filechecker.util.mapping.ViewMapping.INDEX_VIEW;

@Controller
public class UploadController {

    @GetMapping(MAIN_PAGE)
    public String openUploadPage() {
        return INDEX_VIEW;
    }
}
