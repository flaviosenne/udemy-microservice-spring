package com.microservice.auth.repository;

import com.microservice.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("select p from Permission p where p.description = :description")
    Optional<Permission> findByDescription(@Param("description") String description);
}
