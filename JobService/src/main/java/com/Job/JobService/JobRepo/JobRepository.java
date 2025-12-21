package com.Job.JobService.JobRepo;

import com.Job.JobService.JobEntity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {

    @Query("SELECT T FROM JobEntity T WHERE T.jobId = ?1")
    public List<JobEntity> getByJobId(Long id);

}

