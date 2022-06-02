package com.koley.musrights.repositories;

import com.koley.musrights.domains.RentHistoryLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentHistoryRepository extends JpaRepository<RentHistoryLine,  Long> {
}
