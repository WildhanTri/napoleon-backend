package com.example.napoleon.repository;

import java.util.List;
import java.util.Optional;

import com.example.napoleon.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUuid(String uuid);
    
	Optional<User> findByToken(String token);

	Optional<User> findByEmail(String email);

	Optional<User> findByEmailAndPassword(String email, String password);
	
	// List<User> findLikeName(String code);
	
    User save(User user);


	// @Query(value = "SELECT * FROM master_data_form "
	// 		+ "WHERE code = :code and channel = :channel and version = :version ", nativeQuery = true)
	// MasterDataForm findByKeyForm(String code, String channel, String version);
}