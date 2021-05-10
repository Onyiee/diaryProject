package com.onyemowo.diary.repository;

import com.onyemowo.diary.models.Entry;
import com.onyemowo.diary.payload.requestPayload.EntryDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    Slice<Entry> findEntryByTitle(String title, Pageable page);
    Slice<Entry> findEntryByBodyContains(String phrase);
}
