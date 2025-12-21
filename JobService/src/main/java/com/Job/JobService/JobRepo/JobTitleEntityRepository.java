package com.Job.JobService.JobRepo;

import com.Job.JobService.JobEntity.JobTitleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobTitleEntityRepository extends JpaRepository<JobTitleEntity, Long> {
    @Query("SELECT T FROM JobTitleEntity T WHERE T.jobId = ?1")
    public List<JobTitleEntity> getByJobId(Long id);

    @Query("SELECT T FROM JobTitleEntity T WHERE T.entityId = ?1")
    public List<JobTitleEntity> getByEntityId(Long id);
   //@Query("SELECT T FROM JobTitleEntity T WHERE T.jobTitle = ?1")
    public Page<JobTitleEntity> findByJobTitle(String jobTitle, Pageable pageable);
}
