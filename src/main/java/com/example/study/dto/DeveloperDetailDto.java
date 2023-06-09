package com.example.study.dto;

import com.example.study.code.StatusCode;
import com.example.study.entity.DeveloperEntity;
import com.example.study.entity.RetiredDeveloper;
import com.example.study.type.DeveloperLevel;
import com.example.study.type.DeveloperSkillType;
import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDetailDto {

    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private Integer experienceYears;
    private String memberId;
    private StatusCode statusCode;
    private String name;
    private Integer age;

    public static DeveloperDetailDto fromEntity(DeveloperEntity developerEntity){
        return DeveloperDetailDto.builder()
                .developerLevel(developerEntity.getDeveloperLevel())
                .developerSkillType(developerEntity.getDeveloperSkillType())
                .experienceYears(developerEntity.getExperienceYears())
                .memberId(developerEntity.getMemberId())
                .statusCode(developerEntity.getStatusCode())
                .name(developerEntity.getName())
                .age(developerEntity.getAge())
                .build();
    }
}
