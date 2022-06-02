package com.koley.musrights.repositories;

import com.koley.musrights.domains.UserRent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentRepository extends JpaRepository<UserRent, Long> {
    boolean existsByCompositionIdAndUserId(long id, long uid);
    void deleteAllByCompositionId(long id);
    List<UserRent> findAllByUserId(long id);
    UserRent getByUserIdAndCompositionId(long userId, long compositionId);
}
