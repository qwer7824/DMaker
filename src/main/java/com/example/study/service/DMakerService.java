package com.example.study.service;


import com.example.study.dto.CreateDeveloper;
import com.example.study.dto.DeveloperDetailDto;
import com.example.study.dto.DeveloperDto;
import com.example.study.entity.DeveloperEntity;
import com.example.study.exception.DMakerException;
import com.example.study.repository.DeveloperRepository;
import com.example.study.type.DeveloperLevel;
import com.example.study.type.DeveloperSkillType;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.study.exception.DMakerErrorCode.*;

@RequiredArgsConstructor
@Service
public class DMakerService {

    private final DeveloperRepository developerRepository;
    private final EntityManager em;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request){
        validateCreateDeveloperRequest(request);


        // 비지니스 로직
        DeveloperEntity developerEntity = DeveloperEntity.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .memberId(request.getMemberId())
                .name(request.getName())
                .age(request.getAge())
                .build();

            developerRepository.save(developerEntity);
            return CreateDeveloper.Response.fromEntity(developerEntity);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        // validation
        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienceYears = request.getExperienceYears();

        if (developerLevel == DeveloperLevel.SENIOR
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
        developerRepository.findByMemberId(request.getMemberId())
        .ifPresent((developerEntity -> {
            throw new DMakerException(DUPLICATED_MEMBER_ID);
        } ));
    }

    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAll()
                .stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloperDetail(String memberId) {

        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }
}
