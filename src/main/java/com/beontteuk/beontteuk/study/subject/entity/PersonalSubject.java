package com.beontteuk.beontteuk.study.subject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Getter
@NoArgsConstructor
@Table(name = "personal_subject")
public class PersonalSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_subject_id")
    private Long personalSubjectId;

    // FK User : PersonalSubject 1 : N 관계, N인 쪽에 ManyToOne 어노테이션 적용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // FK Exam : PersonalSubject 1 : 1 관계 OneToOne 어노테이션 적용
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @Column(name = "subject_name", nullable = false)
    private String subjectName; // 과목명

    @Lob
    @Column(name = "content")
    private byte[] content;

    @Column(name = "personal_subject_urgency_level")
    private Integer personalSubjectUrgencyLevel;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public PersonalSubject(User user, Exam exam, String subjectName, byte[] content, Integer personalSubjectUrgencyLevel) {
        this.user = user;
        this.exam = exam;
        this.subjectName = subjectName;
        this.content = content;
        this.personalSubjectUrgencyLevel = personalSubjectUrgencyLevel;
        this.createdAt = LocalDateTime.now();
    }
}
