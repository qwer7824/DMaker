package com.example.study.controller;


import com.example.study.dto.*;
import com.example.study.exception.DMakerException;
import com.example.study.service.DMakerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {

    private final DMakerService dMakerService;

    @GetMapping("/developers") // 전체 조회
    public List<DeveloperDto> getAllDevelopers() {
        log.info("Get /developers HTTP/1.1");

        return dMakerService.getAllEmployedDevelopers();
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

    @DeleteMapping("developer/{memberId}") // 사용자 삭제
    public DeveloperDetailDto DeleteDeveloper(
            @PathVariable String memberId){
        {
            log.info("Get /developers HTTP/1.1");

            return dMakerService.deleteDeveloper(memberId);
        }
    }

}
