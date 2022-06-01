package com.example.napoleon.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.example.napoleon.model.Project;
import com.example.napoleon.model.User;
import com.example.napoleon.repository.ProjectRepository;
import com.example.napoleon.repository.UserRepository;
import com.example.napoleon.util.AppUtil;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private HttpServletRequest request;

    public Project findByUuid(String token) {
        Optional<Project> project = projectRepository.findByUuid(token);
        return project.isPresent() ? project.get() : null;
    }

    public List<Project> findProjectByUser(String uuidUser, Integer page, Integer size, String sortBy,
            String sortType) {
        Sort sort = AppUtil.sortType(sortBy, sortType);
        Page<Project> projects = projectRepository.findProjectByUser(uuidUser, PageRequest.of(page, size, sort));

        return projects.getContent();
    }

    public Project update(Project project) {
        return save(project);
    }

    public Project save(Project project) {
        return projectRepository.save(project);
	}

    public void delete(String uuidProject) {
        Project project = projectRepository.findByUuid(uuidProject).get();
        projectRepository.delete(project);
    }

    public void validateProject(String uuidProject, String uuidUser) {
        Optional<Project> project = projectRepository.findProjectByProjectAndUser(uuidProject, uuidUser);
        if(!project.isPresent()) {
            throw new RuntimeException("Unauthorized project");
        }

    }
}
