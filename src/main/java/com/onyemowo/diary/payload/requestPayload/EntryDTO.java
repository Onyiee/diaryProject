package com.onyemowo.diary.payload.requestPayload;

import com.onyemowo.diary.models.Entry;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
@Data
public class EntryDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long entryId;
    private String title;
    private String body;
    private LocalDate createdAt;

    public static Entry unpackDto(EntryDTO entryDTO){
        Entry entry = new Entry();
        entry.setTitle(entryDTO.getTitle());
        entry.setBody(entryDTO.getBody());
        entry.setEntryId(entryDTO.getEntryId());
        entry.setCreatedAt(entryDTO.getCreatedAt());
        return entry;
    }

    public static EntryDTO packDto(Entry entry){
      EntryDTO entryDTO = new EntryDTO();
      entryDTO.setBody(entry.getBody());
      entryDTO.setTitle(entry.getTitle());
      entryDTO.setEntryId(entry.getEntryId());
      entryDTO.setCreatedAt(entry.getCreatedAt());
      return entryDTO;
    }
}
