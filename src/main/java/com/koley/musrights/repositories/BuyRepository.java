package com.koley.musrights.repositories;

import com.koley.musrights.domains.UserBuying;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyRepository extends JpaRepository<UserBuying, Long> {
    boolean existsByCompositionId(long id);
    UserBuying getByCompositionId(long id);
}
