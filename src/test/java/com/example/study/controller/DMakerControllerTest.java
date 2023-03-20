package com.example.study.controller;

import com.example.study.dto.DeveloperDto;
import com.example.study.service.DMakerService;
import com.example.study.type.DeveloperLevel;
import com.example.study.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.example.study.type.DeveloperLevel.*;
import static com.example.study.type.DeveloperSkillType.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DMakerController.class) // 컨트롤러에 관련된 빈 올려서 사용
class DMakerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DMakerService dMakerService;

    protected MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8);

    @Test
    void GetAllDevelopers() throws Exception {
        DeveloperDto juniorDeveloperDto = DeveloperDto.builder()
                        .developerSkillType(DeveloperSkillType.BACK_END)
                        .developerLevel(DeveloperLevel.JUNIOR)
                        .MemberId("MemberId1")
                        .build();
        DeveloperDto seniorDeveloperDto = DeveloperDto.builder()
                        .developerSkillType(DeveloperSkillType.FRONT_END)
                        .developerLevel(DeveloperLevel.SENIOR)
                        .MemberId("MemberId2")
                        .build();
        given(dMakerService.getAllEmployedDevelopers())
                .willReturn(Arrays.asList(juniorDeveloperDto,seniorDeveloperDto));

        mockMvc.perform(get("/developers").contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.[0].developerSkillType",
                                is(DeveloperSkillType.BACK_END.name()))
                )
                .andExpect(
                        jsonPath("$.[0].DeveloperLevel",
                                is(DeveloperLevel.JUNIOR.name()))
                )
                .andExpect(
                        jsonPath("$.[1].developerSkillType",
                                is(DeveloperSkillType.FRONT_END.name()))
                )
                        .andExpect(
                jsonPath("$.[1].DeveloperLevel",
                        is(DeveloperLevel.SENIOR.name()))
        );

    }
}