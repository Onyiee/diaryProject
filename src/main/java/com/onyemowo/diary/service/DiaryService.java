package com.onyemowo.diary.service;

import com.onyemowo.diary.exceptions.DiaryException;
import com.onyemowo.diary.exceptions.EntryException;
import com.onyemowo.diary.models.Diary;
import com.onyemowo.diary.payload.requestPayload.DiaryDTO;
import com.onyemowo.diary.payload.requestPayload.EntryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiaryService {
    void createDiary(DiaryDTO diaryDTO) throws DiaryException;

    DiaryDTO findDiaryByName(String name) throws DiaryException;

    void deleteDiary(String name) throws DiaryException;

    DiaryDTO editDiary(String name, DiaryDTO updatedDetails) throws DiaryException;

    void addNewEntry(int diaryId, EntryDTO newEntry) throws DiaryException;

    void deleteEntry(int diaryId, long entryId) throws EntryException, DiaryException;

    List<EntryDTO> getAllEntries(int diaryId) throws DiaryException;

    EntryDTO getAnEntry(int diaryId, long entryId) throws EntryException, DiaryException;

    void editEntry(int diaryId, long entryId, EntryDTO entryUpdate) throws EntryException, DiaryException;
}
