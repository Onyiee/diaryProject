package com.onyemowo.diary.service;


import com.onyemowo.diary.exceptions.EntryException;
import com.onyemowo.diary.models.Entry;
import com.onyemowo.diary.payload.requestPayload.EntryDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EntryService {
    void createNewEntry (EntryDTO entryDTO);
    void editEntry(EntryDTO entryDTO,long entryId) throws EntryException;
    void deleteEntryById(long entryId);
    EntryDTO getEntryById(long entryId);
    List<EntryDTO> entryBodySearch(String phrase);
}
