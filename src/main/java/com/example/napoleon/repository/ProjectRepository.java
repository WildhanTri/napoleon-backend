package com.example.napoleon.repository;

import java.util.List;
import java.util.Optional;

import com.example.napoleon.model.Project;
import com.example.napoleon.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	Optional<Project> findByUuid(String uuid);
	
	
	@Query(value = "SELECT p FROM Project p JOIN p.user u WHERE p.uuid = :uuidProject AND u.uuid = :uuidUser ")
	Optional<Project> findProjectByProjectAndUser(String uuidProject, String uuidUser);

	@Query(value = "SELECT p FROM Project p JOIN p.user u WHERE u.uuid = :uuidUser ",
	countQuery = "SELECT count(p) FROM Project p JOIN p.user u WHERE u.uuid = :uuidUser")
	Page<Project> findProjectByUser(String uuidUser, Pageable pageable);
	
    Project save(Project user);

}