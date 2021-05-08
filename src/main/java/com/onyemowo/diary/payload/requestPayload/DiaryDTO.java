package com.onyemowo.diary.payload.requestPayload;

import com.onyemowo.diary.models.Diary;
import com.onyemowo.diary.models.Entry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DiaryDTO {
    private String title;
    private int diaryId;
    private String body;
    private LocalDate createdAt;
    private List<Entry> entries = new ArrayList<>();

    public static Diary unpackDto(DiaryDTO diaryDTO){
        Diary diary = new Diary();
        diary.setDiaryId(diaryDTO.getDiaryId());
        diary.setBody(diaryDTO.getBody());
        diary.setTitle(diaryDTO.getTitle());
        diary.setCreatedAt(diaryDTO.getCreatedAt());
        return diary;
    }

    public static DiaryDTO packDto(Diary diary){
        DiaryDTO diaryDTO = new DiaryDTO();
        diaryDTO.setDiaryId(diary.getDiaryId());
        diaryDTO.setBody(diary.getBody());
        diaryDTO.setTitle(diary.getTitle());
        diaryDTO.setCreatedAt(diary.getCreatedAt());
        return diaryDTO;
    }
}
