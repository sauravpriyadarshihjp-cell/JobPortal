package com.Party.PartyService.UWRepository;

import com.Party.PartyService.UWREntity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    public boolean existsByEmail(String email);
}
