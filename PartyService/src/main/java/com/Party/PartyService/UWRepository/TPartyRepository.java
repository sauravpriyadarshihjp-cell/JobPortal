package com.Party.PartyService.UWRepository;

import com.Party.PartyService.UWREntity.TParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TPartyRepository extends JpaRepository<TParty, Long> {

    @Query("SELECT T FROM TParty T WHERE T.partyCode = ?1")
    public List<TParty> getByPartyCode(Long code);
}
