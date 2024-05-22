package com.api.ahsoka.repositories;

import com.api.ahsoka.models.AreaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaRepository extends CrudRepository<AreaEntity, Long> {

    @Query("Select a from AreaEntity a where a.description = ?1")
    Optional<AreaEntity> findByDescription(String description);
}
