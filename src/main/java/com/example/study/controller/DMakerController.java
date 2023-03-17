package com.example.study.controller;


import com.example.study.dto.CreateDeveloper;
import com.example.study.dto.DeveloperDetailDto;
import com.example.study.dto.DeveloperDto;
import com.example.study.dto.EditDeveloper;
import com.example.study.service.DMakerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {

    private final DMakerService dMakerService;

    @GetMapping("/developers") // 전체 조회
    public List<DeveloperDto> getAllDevelopers() {
        log.info("Get /developers HTTP/1.1");

        return dMakerService.getAllDevelopers();
    }

    @GetMapping("/developer/{memberId}") // 상세 조회
    public DeveloperDetailDto getDevelopersDetail(
            @PathVariable String memberId
    ) {
        log.info("Get /developers HTTP/1.1");

        return dMakerService.getDeveloperDetail(memberId);
    }

    @PostMapping("/create-developers") // 사용자 생성
    public CreateDeveloper.Response createDevelopers(
       @Valid @RequestBody CreateDeveloper.Request request){
        {
            log.info("request : {}", request);

            return dMakerService.createDeveloper(request);
        }
    }

    @PutMapping("developer/{memberId}") // 사용자 수정
    public DeveloperDetailDto editDeveloper(
            @PathVariable String memberId,
            @Valid @RequestBody EditDeveloper.Request request){
        {
            log.info("Get /developers HTTP/1.1");

            return dMakerService.editDeveloper(memberId,request);
        }
    }

}
