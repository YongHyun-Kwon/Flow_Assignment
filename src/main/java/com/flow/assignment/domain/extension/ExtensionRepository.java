package com.flow.assignment.domain.extension;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExtensionRepository extends JpaRepository<Extension, Long> {

    @Query("SELECT COUNT(e) FROM Extension e WHERE e.type = 'CUSTOM'")
    long countAllExtensions();

    boolean existsByName(String name);
}
