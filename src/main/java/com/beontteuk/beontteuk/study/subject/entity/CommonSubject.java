package com.beontteuk.beontteuk.study.subject.entity;

import jakarta.persistence.Table;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "common_subject")
public class CommonSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_common_id")
    private Long subjectCommonId;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Lob
    @Column(name = "content")
    private byte[] content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public CommonSubject(String subjectName, byte[] content) {
        this.subjectName = subjectName;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
