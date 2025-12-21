package com.Job.JobService.JobRepo;

import com.Job.JobService.JobEntity.IDEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDRepository extends JpaRepository<IDEntity, Long> {

    @Query("SELECT T FROM IDEntity T WHERE T.Proposalid = ?1")
    public List<IDEntity> getByProposalId(Long id);

    @Query("SELECT T FROM IDEntity T WHERE T.jobId = ?1")
    public List<IDEntity> getByJobId(Long id);
}
