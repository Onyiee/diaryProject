package com.onyemowo.diary.service;

import com.onyemowo.diary.exceptions.EntryException;
import com.onyemowo.diary.models.Entry;
import com.onyemowo.diary.payload.requestPayload.EntryDTO;
import com.onyemowo.diary.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EntryServiceImpl implements EntryService {
    @Autowired
    private EntryRepository entryRepository;

    @Override
    public void createNewEntry(EntryDTO entryDTO) {
        Entry entry = EntryDTO.unpackDto(entryDTO);
        entry.setCreatedAt(LocalDate.now());
        entryRepository.save(entry);

    }

    @Override
    public void editEntry(EntryDTO entryDTO, long entryId) throws EntryException {
        Optional<Entry> entry = entryRepository.findById(entryId);
        Entry foundEntry;
        if (entry.isPresent()) {
            foundEntry = entry.get();
        } else {
            throw new EntryException("No entry found with that ID");
        }
        foundEntry.setTitle(entryDTO.getTitle());
        foundEntry.setBody(entryDTO.getBody());
        entryRepository.save(foundEntry);
    }

    @Override
    public void deleteEntryById(long entryId) {
        entryRepository.deleteById(entryId);
    }

    @Override
    public EntryDTO getEntryById(long entryId) {
        EntryDTO entryDTO = null;
        Optional<Entry> entry = entryRepository.findById(entryId);
        if(entry.isPresent()){
            entryDTO = EntryDTO.packDto(entry.get());
        }
        return entryDTO;
    }

    @Override
    public List<EntryDTO> entryBodySearch(String phrase) {
        Slice<Entry> entry = entryRepository.findEntryByBodyContains(phrase);
        List<EntryDTO> entryDTOS = EntryDTO.packDto(entry.stream().reduce());
    }

}
