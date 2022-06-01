package com.example.napoleon.service;

import java.util.List;
import java.util.Optional;

import com.example.napoleon.model.ReportData;
import com.example.napoleon.repository.ReportDataRepository;
import com.example.napoleon.util.AppUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReportDataService {

    @Autowired
    private ReportDataRepository reportDataRepository;

    public ReportData findByUuid(String token) {
        Optional<ReportData> reportData = reportDataRepository.findByUuid(token);
        return reportData.isPresent() ? reportData.get() : null;
    }

    public List<ReportData> findReportDataByProject(String uuidProject, Integer page, Integer size, String sortBy,
            String sortType) {
        Sort sort = AppUtil.sortType(sortBy, sortType);
        Page<ReportData> reportDatas = reportDataRepository.findReportDataByProject(uuidProject, PageRequest.of(page, size, sort));

        return reportDatas.getContent();
    }

    public ReportData update(ReportData reportData) {
        return save(reportData);
    }

    public ReportData save(ReportData reportData) {
        return reportDataRepository.save(reportData);
	}

    public void delete(String uuidReportData) {
        ReportData reportData = reportDataRepository.findByUuid(uuidReportData).get();
        reportDataRepository.delete(reportData);
    }
}
