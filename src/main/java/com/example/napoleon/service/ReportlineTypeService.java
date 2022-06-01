package com.example.napoleon.service;

import java.util.List;
import java.util.Optional;

import com.example.napoleon.model.ReportlineType;
import com.example.napoleon.repository.ReportlineTypeRepository;
import com.example.napoleon.util.AppUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReportlineTypeService {
    
    @Autowired
    private ReportlineTypeRepository reportlineTypeRepository;

    
    public ReportlineType findByUuid(String token) {
        Optional<ReportlineType> reportlineType = reportlineTypeRepository.findByUuid(token);
        return reportlineType.isPresent() ? reportlineType.get() : null;
    }
    
    public List<ReportlineType> find(Integer page, Integer size, String sortBy,
            String sortType) {
        Sort sort = AppUtil.sortType(sortBy, sortType);
        Page<ReportlineType> reportlineTypes = reportlineTypeRepository.findReportlineType(PageRequest.of(page, size, sort));

        return reportlineTypes.getContent();
    }
}
