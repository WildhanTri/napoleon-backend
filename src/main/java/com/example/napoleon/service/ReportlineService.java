package com.example.napoleon.service;

import java.util.List;
import java.util.Optional;

import com.example.napoleon.model.Reportline;
import com.example.napoleon.repository.ReportlineRepository;
import com.example.napoleon.util.AppUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReportlineService {

    @Autowired
    private ReportlineRepository reportlineRepository;

    public Reportline findByUuid(String token) {
        Optional<Reportline> reportline = reportlineRepository.findByUuid(token);
        return reportline.isPresent() ? reportline.get() : null;
    }

    public List<Reportline> findReportlineByProject(String uuidProject, Integer page, Integer size, String sortBy,
            String sortType) {
        Sort sort = AppUtil.sortType(sortBy, sortType);
        Page<Reportline> reportlines = reportlineRepository.findReportlineByProject(uuidProject, PageRequest.of(page, size, sort));

        return reportlines.getContent();
    }

    public Reportline update(Reportline reportline) {
        return save(reportline);
    }

    public Reportline save(Reportline reportline) {
        return reportlineRepository.save(reportline);
	}

    public void delete(String uuidReportline) {
        Reportline reportline = reportlineRepository.findByUuid(uuidReportline).get();
        reportlineRepository.delete(reportline);
    }

    public void validateReportline(String uuidReportline, String uuidProject) {
        Optional<Reportline> reportline = reportlineRepository.findReportlineByReportlineAndProject(uuidReportline, uuidProject);
        if(!reportline.isPresent()) {
            throw new RuntimeException("Unauthorized reportline");
        }

    }
}
