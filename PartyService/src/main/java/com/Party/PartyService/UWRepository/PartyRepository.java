package com.Party.PartyService.UWRepository;

import com.Party.PartyService.UWREntity.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<PartyEntity, Long> {

    @Query("SELECT T FROM PartyEntity T WHERE T.partyId = ?1")
    public List<PartyEntity> getByPartyId(Long id);
}
