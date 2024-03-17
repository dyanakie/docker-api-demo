package com.example.demo.controllers;

import com.example.demo.controllers.dto.DataItem;
import com.example.demo.controllers.dto.EmailWrapper;
import com.example.demo.service.EmailGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class EmailController {

    private final EmailGeneratorService emailGeneratorService;

    @GetMapping("/generate-email")
    public EmailWrapper generateEmail(@RequestParam Map<String,String> allParams) {
        String expression = allParams.remove("expression");
        String email = emailGeneratorService.generateEmail(allParams, expression);

        DataItem item = new DataItem(email, email);
        EmailWrapper wrapper = new EmailWrapper(Collections.singletonList(item));

        return wrapper;
    }
}
