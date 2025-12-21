package com.Party.PartyService.UWREntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="Party_Parameters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartyValues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long partyId;

    @Column
    private String email;

    @Column
    private String Name;

    @Column
    private String mobileNo;

    @Column
    private String skills;

    @Column(name = "cratedAt",updatable = false)
    @CreationTimestamp
    private LocalDateTime dateTime;

}
