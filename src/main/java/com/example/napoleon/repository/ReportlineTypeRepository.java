package com.example.napoleon.repository;

import java.util.Optional;

import com.example.napoleon.model.ReportlineType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportlineTypeRepository extends JpaRepository<ReportlineType, Long> {

	Optional<ReportlineType> findByUuid(String uuid);

	@Query(value = "SELECT rt FROM ReportlineType rt ",
	countQuery = "SELECT count(rt) FROM ReportlineType rt")
	Page<ReportlineType> findReportlineType(Pageable pageable);

}