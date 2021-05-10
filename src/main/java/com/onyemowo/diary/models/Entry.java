package com.onyemowo.diary.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "entries")

public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long entryId;
    private String title;
    private String body;
    private LocalDate createdAt;
}
