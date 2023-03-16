package com.example.study.service;


import com.example.study.dto.CreateDeveloper;
import com.example.study.entity.DeveloperEntity;
import com.example.study.exception.DMakerException;
import com.example.study.repository.DeveloperRepository;
import com.example.study.type.DeveloperLevel;
import com.example.study.type.DeveloperSkillType;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.study.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.example.study.exception.DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED;

@RequiredArgsConstructor
@Service
public class DMakerService {

    private final DeveloperRepository developerRepository;
    private final EntityManager em;

    @Transactional
    public void createDeveloper(CreateDeveloper.Request request){
        validateCreateDeveloperRequest(request);


        // 비지니스 로직
        DeveloperEntity developerEntity = DeveloperEntity.builder()
                .developerLevel(DeveloperLevel.NEW)
                .developerSkillType(DeveloperSkillType.Back_end)
                .experienceYears(2)
                .name("a")
                .age(5)
                .build();

            developerRepository.save(developerEntity);
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

}
