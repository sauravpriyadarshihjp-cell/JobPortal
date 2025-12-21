package com.Resume.ResumeService.RsumEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "Resume_Dtl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;

    @Column
    private Long partyId;

    @Column
    private String createdBy;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime creationTime;

    @Column
    private String resumeFileName;

    @Column
    private String resumeContentType;

    @Column(columnDefinition = "TEXT")
    private String resumeText;

    @Column
    private String resumePath;
}
