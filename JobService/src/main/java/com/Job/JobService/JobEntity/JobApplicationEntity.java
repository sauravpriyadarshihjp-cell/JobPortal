package com.Job.JobService.JobEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_Apply")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @Column
    private Long partyId;

    @Column
    private Long jobId;

    @Column
    private String applicationStatus;

    @Column
    private String insertedBy;

    @Column(name = "creation_time",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

}
