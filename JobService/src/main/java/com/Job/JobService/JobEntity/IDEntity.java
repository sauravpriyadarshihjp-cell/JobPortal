package com.Job.JobService.JobEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "job")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IDEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Proposalid;

    private Long jobId;

    @Column
    private String createdBy;

    @Column
    private String updatedBy;

    @Column(name = "creation_time",updatable = false)
    @CreationTimestamp
    private LocalDateTime time;

    private static final AtomicLong counter = new AtomicLong();
    @PrePersist
    public void prepersist(){
        if(jobId == null){
            jobId = System.currentTimeMillis() * 1000 + counter.getAndIncrement();
        }
    }

}
