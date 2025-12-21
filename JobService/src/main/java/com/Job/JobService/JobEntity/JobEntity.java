package com.Job.JobService.JobEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_Property")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creationid;

    @Column
    private Long jobId;

    @Column
    private String ParameterName;

    @Column
    private String ParameterValue;

    @Column
    private Long partyId;

    @Column
    private String insertedBy;

    @Column
    private String updatedBy;

    @Column(name = "Creation_time",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @PrePersist
    public void hii(){
        System.out.println("Hello Saurav");

    }
}
