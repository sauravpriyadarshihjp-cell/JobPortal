package com.Job.JobService.JobRepo;

import com.Job.JobService.JobEntity.JobApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplyRepository extends JpaRepository<JobApplicationEntity, Long> {

    @Query("SELECT T FROM JobApplicationEntity T WHERE T.partyId = ?1 and T.jobId = ?2")
    public JobApplicationEntity getByjobIdandPArtyId(Long partyId, Long jobId);

}
