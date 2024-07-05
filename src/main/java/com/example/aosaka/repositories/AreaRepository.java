package com.example.aosaka.repositories;
import com.example.aosaka.models.AreaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends CrudRepository<AreaEntity, Long> {
}
