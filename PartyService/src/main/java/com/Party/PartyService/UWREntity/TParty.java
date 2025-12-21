package com.Party.PartyService.UWREntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "party")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TParty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyCode;

    @Column
    private Long partyId;

    @Column
    private String partyType;

    @Column
    private String insertedBy;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime creationTime;

    private static final AtomicLong counter = new AtomicLong();
    @PrePersist
    public void prepersist(){
        if(partyId == null){
            partyId = System.currentTimeMillis() * 100 + counter.getAndIncrement();
        }
    }
}
