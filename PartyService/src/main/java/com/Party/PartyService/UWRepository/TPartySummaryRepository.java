package com.Party.PartyService.UWRepository;

import com.Party.PartyService.UWREntity.PartyValues;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TPartySummaryRepository extends JpaRepository<PartyValues,  Long> {
    public List<PartyValues> findBySkillsContaining(String text);
}
