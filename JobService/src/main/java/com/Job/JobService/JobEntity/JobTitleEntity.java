package com.Job.JobService.JobEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_Title_Property")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobTitleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;

    @Column
    private String jobTitle;

    @Column
    private String jobCompany;

    @Column
    private Long jobId;

    @Column
    private String jobDesc;

    @Column
    private String createdBy;

    @Column
    private String updatedBy;

    @Column
    private String salary;

    @Column
    private String location;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime creationTime;


}
