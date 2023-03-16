package com.example.study.controller;


import com.example.study.dto.CreateDeveloper;
import com.example.study.service.DMakerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {

    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<String> getAllDevelopers() {
        log.info("Get /developers HTTP/1.1");

        return Arrays.asList("a", "b", "c");
    }

    @PostMapping("/developers")
    public List<String> createDevelopers(
       @Valid @RequestBody CreateDeveloper.Request request){
        {
            log.info("request : {}", request);

            dMakerService.createDeveloper(request);

            return Collections.singletonList("a");
        }
    }
}
