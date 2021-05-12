package com.onyemowo.diary.repository;

import com.onyemowo.diary.models.Diary;
import com.onyemowo.diary.models.Entry;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary,Integer> {
    Optional<Diary> findByName (String name);
    boolean existsByName(String name);
}
