package com.example.study.type;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeveloperSkillType {

    Back_end("백엔드 개발자"),
    FRONT_END("프론트 개발자"),
    FULL_STACK("풀스택 개발자");

    private final String description;
}
