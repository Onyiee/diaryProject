package com.onyemowo.diary.service;

import com.onyemowo.diary.exceptions.EntryException;
import com.onyemowo.diary.models.Entry;
import com.onyemowo.diary.payload.requestPayload.EntryDTO;
import com.onyemowo.diary.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
    public void deleteEntryById(long entryId) throws EntryException {
       Entry entry = entryRepository.findById(entryId)
                .orElseThrow(
                        ()-> new EntryException("no entry found with that ID")
                );
        entryRepository.delete(entry);
    }

    @Override
    public EntryDTO getEntryById(long entryId) throws EntryException {
        EntryDTO entryDTO = null;
        Optional<Entry> entry = entryRepository.findById(entryId);
        if(entry.isPresent()){
            entryDTO = EntryDTO.packDto(entry.get());
        } else {
            throw new EntryException("Found no entry with that ID.");
        }
        return entryDTO;
    }

    @Override
    public List<EntryDTO> entryBodySearch(String phrase, int pageNumber) {
        int pageContent = 1;
        Pageable page = PageRequest.of(pageNumber, pageContent);
        Slice<Entry> entry = entryRepository.findEntryByBodyContains(phrase, page);
        List<EntryDTO> entries = entry.stream().map(
                (entryObject) -> {
                    EntryDTO dto = EntryDTO.packDto(entryObject);
                    return dto;
                }
        ).collect(Collectors.toList());
        return entries;
    }

    @Override
    public List<EntryDTO> getAllEntries(int pageNumber) {
        int pageContent = 2;
        Pageable page = PageRequest.of(pageNumber,pageContent);
        Page<Entry> entries = entryRepository.findAll(page);
        List<EntryDTO> allEntries = entries.stream().map(
                (singleEntry)-> {
                    EntryDTO entryDTO = EntryDTO.packDto(singleEntry);
                    return entryDTO;
                }
        ).collect(Collectors.toList());
        return allEntries;
    }

}
