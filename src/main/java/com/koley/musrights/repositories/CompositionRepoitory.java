package com.koley.musrights.repositories;

import com.koley.musrights.domains.Composition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompositionRepoitory extends JpaRepository<Composition, Long> {
    void deleteAll();
    boolean existsByNameAndAuthor(String name, String author);
}
