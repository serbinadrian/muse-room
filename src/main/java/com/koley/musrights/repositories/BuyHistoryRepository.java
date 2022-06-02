package com.koley.musrights.repositories;

import com.koley.musrights.domains.BuyingHistoryLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyHistoryRepository extends JpaRepository<BuyingHistoryLine, Long> {
}
