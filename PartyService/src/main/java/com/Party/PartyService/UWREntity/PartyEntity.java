package com.Party.PartyService.UWREntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Party_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creationId;

    @Column
    private Long partyId;

    @Column
    private String parameterName;

    @Column
    private String parameterValue;

    @Column
    private String updatedBy;

    @Column
    private String insertedBy;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime creationTime;

    @Column
    private String partyType;
}
