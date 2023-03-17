package com.example.study.repository;

import com.example.study.code.StatusCode;
import com.example.study.entity.DeveloperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<DeveloperEntity , Long> {

    Optional<DeveloperEntity> findByMemberId(String memberId);

    List<DeveloperEntity> findDeveloperEntitiesByStatusCodeEquals(StatusCode statusCode);

}
