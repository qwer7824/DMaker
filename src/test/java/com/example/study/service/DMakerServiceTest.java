package com.example.study.service;

import com.example.study.code.StatusCode;
import com.example.study.dto.CreateDeveloper;
import com.example.study.dto.DeveloperDetailDto;
import com.example.study.dto.DeveloperDto;
import com.example.study.entity.DeveloperEntity;
import com.example.study.repository.DeveloperRepository;
import com.example.study.repository.RetiredDeveloperRepository;
import com.example.study.type.DeveloperLevel;
import com.example.study.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.example.study.code.StatusCode.EMPLOYED;
import static com.example.study.type.DeveloperLevel.*;
import static com.example.study.type.DeveloperSkillType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;
    @InjectMocks
    private DMakerService dMakerService;

    @Test
    public void testSomething(){
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(DeveloperEntity.builder()
                        .developerLevel(SENIOR)
                        .developerSkillType(FRONT_END)
                        .experienceYears(12)
                        .statusCode(EMPLOYED)
                        .name("name")
                        .age(12)
                        .build()));
        DeveloperDetailDto developerDetail = dMakerService.getDeveloperDetail("memberId");

        assertEquals(SENIOR,developerDetail.getDeveloperLevel());
        assertEquals(FRONT_END,developerDetail.getDeveloperSkillType());
        assertEquals(12,developerDetail.getExperienceYears());
    }



    @Test
    void createdDeveloperTest_success(){
    // Given
        CreateDeveloper.Request request = CreateDeveloper.Request.builder()
                .developerLevel(SENIOR)
                .developerSkillType(FRONT_END)
                .experienceYears(12)
                .memberId("memberId")
                .name("name")
                .age(32)
                .build();

        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());
        ArgumentCaptor<DeveloperDto> captor =
                ArgumentCaptor.forClass(DeveloperDto.class);

    // When
        CreateDeveloper.Response developer = dMakerService.createDeveloper(request);
        // Then

        verify(developerRepository, times(1))
                .save(captor.capture());// 특정 목이 몇번 호출됐다.

    }
}