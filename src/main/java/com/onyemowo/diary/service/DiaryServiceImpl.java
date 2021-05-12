package com.onyemowo.diary.service;

import com.onyemowo.diary.exceptions.DiaryException;
import com.onyemowo.diary.exceptions.EntryException;
import com.onyemowo.diary.models.Diary;
import com.onyemowo.diary.models.Entry;
import com.onyemowo.diary.payload.requestPayload.DiaryDTO;
import com.onyemowo.diary.payload.requestPayload.EntryDTO;
import com.onyemowo.diary.payload.responsePayload.ApiResponse;
import com.onyemowo.diary.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DiaryServiceImpl implements DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private EntryService entryService;

    @Override
    public void createDiary(DiaryDTO diaryDTO) throws DiaryException {
        String diaryName = diaryDTO.getName();
        if (diaryRepository.existsByName(diaryName)) {
            throw new DiaryException("Diary name already exists");
        }
        Diary diary = DiaryDTO.unpackDto(diaryDTO);
        diary.setCreatedAt(LocalDate.now());
        diaryRepository.save(diary);
    }

    @Override
    public DiaryDTO findDiaryByName(String name) throws DiaryException {
        Optional<Diary> diary = diaryRepository.findByName(name);
        if (diary.isEmpty()) {
            throw new DiaryException("Found no diary with that name");
        }

        DiaryDTO diaryDTO = DiaryDTO.packDto(diary.get());
        return diaryDTO;
    }

    @Override
    public void deleteDiary(String name) throws DiaryException {
        Optional<Diary> diary = diaryRepository.findByName(name);
        if (diary.isPresent()) {
            diaryRepository.delete(diary.get());
        } else {
            throw new DiaryException("Found no diary with that name.");
        }

    }

    @Override
    public DiaryDTO editDiary(String name, DiaryDTO updatedDetails) throws DiaryException {
        Optional<Diary> diary = diaryRepository.findByName(name);
        if (diary.isEmpty()) {
            throw new DiaryException("No diary found.");
        }
        Diary newDiary = diary.get();
        newDiary.setName(updatedDetails.getName());
        Diary updatedDiary = diaryRepository.save(newDiary);
        return DiaryDTO.packDto(updatedDiary);
    }

    @Override
    public void addNewEntry(int dairyId, EntryDTO newEntry) throws DiaryException {
        Diary diary = diaryRepository.findById(dairyId)
                .orElseThrow(() -> new DiaryException("No diary found with that Id"));
        Entry savedEntry = entryService.internalCreateEntry(newEntry);
        diary.getEntries().add(savedEntry);
        diaryRepository.save(diary);
    }

    @Override
    public void deleteEntry(int diaryId, long entryId) throws EntryException, DiaryException {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new DiaryException("No diary found with that ID."));
        Optional<Entry> entryOptional
                = diary.getEntries().stream().filter(entry -> entry.getEntryId() == entryId).findFirst();

        Entry foundEntry = entryOptional.orElseThrow(
                ()-> new EntryException("This Entry does not belong to diary with id " + diaryId)
        );
        diary.getEntries().remove(foundEntry);
        entryService.deleteEntryById(foundEntry.getEntryId());
    }

    @Override
    public List<EntryDTO> getAllEntries(int diaryId) throws DiaryException {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(()-> new DiaryException("No diary found with that ID."));
        return diary.getEntries().stream().map(EntryDTO::packDto).collect(Collectors.toList());
    }

    @Override
    public EntryDTO getAnEntry(int diaryId,long entryId) throws EntryException, DiaryException {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(()-> new DiaryException("No diary found with that ID."));
        Optional<Entry> entryOptional =
                diary.getEntries().stream().filter(entry -> entry.getEntryId()==entryId).findFirst();
        if (entryOptional.isEmpty()){
            throw new DiaryException("This diary does not contain any entry with id " + entryId);
        }
        return EntryDTO.packDto(entryOptional.get());
    }

    @Override
    public void editEntry(int diaryId ,long entryId, EntryDTO entryUpdate) throws EntryException, DiaryException {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(()-> new DiaryException("No diary found with that ID."));
        Optional<Entry> entryOptional =
                diary.getEntries().stream().filter(entry -> entry.getEntryId()==entryId).findFirst();
        if (entryOptional.isEmpty()){
            throw new DiaryException("This diary does not contain any entry with id " + entryId);
        }
        long entryToUpdateId = entryOptional.get().getEntryId();
        entryService.editEntry(entryUpdate, entryToUpdateId);

    }
}
