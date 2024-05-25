package com.api.ahsoka.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.ahsoka.models.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{
	
	Optional<UserEntity> findByUsername(String username);

	Optional<UserEntity> findByEmail(String email);

	@Query("Select u from UserEntity u where u.username=?1")
	Optional<UserEntity> getName(String username);

}
