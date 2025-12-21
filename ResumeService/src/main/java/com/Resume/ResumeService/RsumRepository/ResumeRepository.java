package com.Resume.ResumeService.RsumRepository;

import com.Resume.ResumeService.RsumEntity.ResumeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<ResumeEntity, Long> {
    public List<ResumeEntity> findByResumeTextContainingIgnoreCase(String keyword);

    public List<ResumeEntity> findByPartyId(Long partyId);

}
