package com.example.study.dto;

import com.example.study.entity.DeveloperEntity;
import com.example.study.type.DeveloperLevel;
import com.example.study.type.DeveloperSkillType;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeveloperDto {
    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private String MemberId;

    public static DeveloperDto fromEntity(DeveloperEntity developerEntity){
        return DeveloperDto.builder()
                .developerLevel(developerEntity.getDeveloperLevel())
                .developerSkillType(developerEntity.getDeveloperSkillType())
                .MemberId(developerEntity.getMemberId())
                .build();
    }
}
