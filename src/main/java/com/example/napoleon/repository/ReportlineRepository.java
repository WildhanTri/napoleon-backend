package com.example.napoleon.repository;

import java.util.Optional;

import com.example.napoleon.model.Reportline;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportlineRepository extends JpaRepository<Reportline, Long> {

	Optional<Reportline> findByUuid(String uuid);

	@Query(value = "SELECT r FROM Reportline r JOIN r.project p WHERE r.uuid = :uuidReportline AND p.uuid = :uuidProject ")
	Optional<Reportline> findReportlineByReportlineAndProject(String uuidReportline, String uuidProject);

	@Query(value = "SELECT r FROM Reportline r JOIN r.project p WHERE p.uuid = :uuidProject ",
	countQuery = "SELECT count(r) FROM Reportline r JOIN r.project p WHERE r.uuid = :uuidProject")
	Page<Reportline> findReportlineByProject(String uuidProject, Pageable pageable);
	
    Reportline save(Reportline reportline);

}