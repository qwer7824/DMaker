package com.example.study.dto;

import com.example.study.code.StatusCode;
import com.example.study.entity.DeveloperEntity;
import com.example.study.type.DeveloperLevel;
import com.example.study.type.DeveloperSkillType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

public class CreateDeveloper {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request{

        @NotNull
        private DeveloperLevel developerLevel;
        @NotNull
        private DeveloperSkillType developerSkillType;
        @NotNull
        @Min(0)
        @Max(20)
        private Integer experienceYears;

        @NotNull
        @Size(min = 3, max = 50, message = "memberId size must 3 ~ 50")
        private String memberId;
        @NotNull
        @Size(min = 3, max = 20, message = "memberId size must 3 ~ 20")
        private String name;
        @Min(18)
        private Integer age;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private DeveloperLevel developerLevel;
        private DeveloperSkillType developerSkillType;
        private Integer experienceYears;
        private String memberId;


        public static Response fromEntity(DeveloperEntity developerEntity){
            return Response.builder()
                    .developerLevel(developerEntity.getDeveloperLevel())
                    .developerSkillType(developerEntity.getDeveloperSkillType())
                    .experienceYears(developerEntity.getExperienceYears())
                    .memberId(developerEntity.getMemberId())
                    .build();
        }
    }
}
