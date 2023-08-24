package com.flow.assignment.domain.extension;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomExtensionRepository extends JpaRepository<CustomExtension, Long> {

    @Query("SELECT COUNT(c) FROM CustomExtension c")
    long countAllCustomExtensions();

    @Query("SELECT COUNT(e.name) + COUNT(c.name) FROM CustomExtension c, Extension e WHERE c.name = :name OR e.name = :name")
    long countByNameOrExtensionName(String name);


}
