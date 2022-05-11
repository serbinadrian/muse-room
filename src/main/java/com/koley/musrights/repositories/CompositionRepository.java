package com.koley.musrights.repositories;

import com.koley.musrights.domains.Composition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompositionRepository extends JpaRepository<Composition, Long> {
    List<Composition> findAllByOwnerId(long id);
    void deleteAll();
    boolean existsByNameAndAuthor(String name, String author);
}
