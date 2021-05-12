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
    private String name;
    private LocalDate createdAt;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "entryId")
    private List<Entry> entries=new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int diaryId;
}
