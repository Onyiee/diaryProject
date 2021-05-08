package com.onyemowo.diary.service;

import com.onyemowo.diary.models.Entry;
import com.onyemowo.diary.payload.requestPayload.EntryDTO;
import com.onyemowo.diary.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

public class EntryServiceImpl implements EntryService{
    @Autowired
    private EntryRepository entryRepository;

    @Override
    public void createNewEntry(EntryDTO entryDTO) {
        Entry entry = EntryDTO.unpackDto(entryDTO);
        entry.setCreatedAt(LocalDate.now());
        entryRepository.save(entry);

    }

    @Override
    public void editEntryTitle(EntryDTO entryDTO) {
        Entry entry = EntryDTO.unpackDto(entryDTO);
        if(getEntriesById(entry.getEntryId()).isPresent()){
            entryRepository.deleteById(entry.getEntryId());
            createNewEntry(entryDTO);
        }else {
            createNewEntry(entryDTO);
        }

    }

    @Override
    public void deleteEntryById(int entryId) {
        entryRepository.deleteById(entryId);
    }

    @Override
    public Optional<Entry> getEntriesById(int entryId) {
        return entryRepository.findById(entryId);
    }

//    @Override
//    public List<EntryDTO> getEntriesByBodyContaining(String phrase) {
//        List<Entry> entries=entryRepository.findAll();
//        if(entries.contains(phrase)){
//
//        }
//
//        return null;
//    }
}
