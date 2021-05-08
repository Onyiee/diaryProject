package com.onyemowo.diary.service;


import com.onyemowo.diary.models.Entry;
import com.onyemowo.diary.payload.requestPayload.EntryDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EntryService {
    void createNewEntry (EntryDTO entryDTO);
    void editEntryTitle (EntryDTO entryDTO);
    void deleteEntryById(int entryId);
    Optional<Entry> getEntriesById(int entryId);
//    List<EntryDTO> getEntriesByBodyContaining(String phrase);
}
