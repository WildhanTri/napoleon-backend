package com.example.napoleon.repository;

import java.util.Optional;

import com.example.napoleon.model.ReportData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDataRepository extends JpaRepository<ReportData, Long> {

	Optional<ReportData> findByUuid(String uuid);

	@Query(value = "SELECT rd FROM ReportData rd JOIN rd.reportline r JOIN r.project p WHERE p.uuid = :uuidProject ",
	countQuery = "SELECT count(rd) FROM ReportData rd JOIN rd.reportline r JOIN r.project p WHERE r.uuid = :uuidProject")
	Page<ReportData> findReportDataByProject(String uuidProject, Pageable pageable);
	
    ReportData save(ReportData reportData);

}