package com.onyemowo.diary.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "diary")

public class Diary {
    private String title;
    private String body;
    private LocalDate createdAt;
    private List<Entry> entries = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private List<Entry> arrayList=new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int diaryId;
}
