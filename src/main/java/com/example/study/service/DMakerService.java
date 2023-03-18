package com.example.study.service;


import ch.qos.logback.core.spi.ErrorCodes;
import com.example.study.code.StatusCode;
import com.example.study.dto.CreateDeveloper;
import com.example.study.dto.DeveloperDetailDto;
import com.example.study.dto.DeveloperDto;
import com.example.study.dto.EditDeveloper;
import com.example.study.entity.DeveloperEntity;
import com.example.study.entity.RetiredDeveloper;
import com.example.study.exception.DMakerErrorCode;
import com.example.study.exception.DMakerException;
import com.example.study.repository.DeveloperRepository;
import com.example.study.repository.RetiredDeveloperRepository;
import com.example.study.type.DeveloperLevel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.study.exception.DMakerErrorCode.*;
import static com.example.study.type.DeveloperLevel.SENIOR;

@Service
@RequiredArgsConstructor
public class DMakerService {

    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;


    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request){
        validateCreateDeveloperRequest(request);


        // 비지니스 로직
        DeveloperEntity developerEntity = DeveloperEntity.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .memberId(request.getMemberId())
                .statusCode(StatusCode.EMPLOYED)
                .name(request.getName())
                .age(request.getAge())
                .build();

            developerRepository.save(developerEntity);
            return CreateDeveloper.Response.fromEntity(developerEntity);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        // validation
        validateDeveloperLevel
                (request.getDeveloperLevel(),
                        request.getExperienceYears());

            developerRepository.findByMemberId(request.getMemberId())
                    .ifPresent((developerEntity -> {
                        throw new DMakerException(DUPLICATED_MEMBER_ID);
                    }));


    }

    public List<DeveloperDto> getAllEmployedDevelopers() {
        return developerRepository.findDeveloperEntitiesByStatusCodeEquals(StatusCode.EMPLOYED)
                .stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloperDetail(String memberId) {

        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }

    @Transactional
    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        validateEditDeveloperRequest(request, memberId);

        DeveloperEntity developerEntity = developerRepository.findByMemberId(memberId).orElseThrow(
                () -> new DMakerException(NO_DEVELOPER)
        );

        developerEntity.setDeveloperLevel(request.getDeveloperLevel());
        developerEntity.setDeveloperSkillType(request.getDeveloperSkillType());
        developerEntity.setExperienceYears(request.getExperienceYears());

        return DeveloperDetailDto.fromEntity(developerEntity);
    }


    private void validateEditDeveloperRequest(
            EditDeveloper.Request request,
            String memberId) {
        validateDeveloperLevel
                (request.getDeveloperLevel(),
                        request.getExperienceYears())
        ;
    }

    private void validateDeveloperLevel(DeveloperLevel developerLevel, Integer experienceYears) {
        if (developerLevel == SENIOR
                && experienceYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNGNIOR
                && (experienceYears < 4 || experienceYears > 10)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if(developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4){
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
    }

    @Transactional
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        // 1. EMPLOYED -> RETIRED
        DeveloperEntity developerEntity = developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
        developerEntity.setStatusCode(StatusCode.RETIRED);

        if(developerEntity != null)
        throw new DMakerException(NO_DEVELOPER);

        // 2. save into RetiredDeveloper
        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(memberId)
                .name(developerEntity.getName())
                .build();

        retiredDeveloperRepository.save(retiredDeveloper);
        return DeveloperDetailDto.fromEntity(developerEntity);
    }

}
