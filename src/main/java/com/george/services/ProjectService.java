package com.george.services;

import com.george.dto.ProjectFull;
import com.george.dto.ProjectLongId;
import com.george.entities.Project;
import com.george.mappers.ProjectMapper;
import com.george.repositories.ProjectRepository;
import com.george.support.InvalidIdentifierException;
import com.george.support.PrimaryKeyViolationException;
import com.george.support.RequiredFieldMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ProjectMapper mapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = mapper;
    }

    public void createAllProjects(ProjectLongId[] projectsLongId) throws PrimaryKeyViolationException, NoSuchFieldException, RequiredFieldMissingException {
        for (ProjectLongId projectLongId : projectsLongId)
            createProject(projectLongId);
    }

    public void createProject(ProjectLongId projectLongId) throws PrimaryKeyViolationException, NoSuchFieldException, RequiredFieldMissingException {
        Long id = projectLongId.getId();
        if(id != null && projectRepository.existsById(id))
            throw new PrimaryKeyViolationException("Project", id);
        Project project = projectMapper.mapToEntity(projectLongId);
        projectRepository.save(project);
    }

    public Iterable<ProjectLongId> getAllProjects(){
        return StreamSupport.stream(projectRepository.findAll().spliterator(), false)
                .map(projectMapper::mapToLongId)
                .collect(Collectors.toList());
    }

    public ProjectLongId getProjectById(Long id) throws InvalidIdentifierException {
        if(!projectRepository.existsById(id))
            throw new InvalidIdentifierException("Project", id);
        return projectMapper.mapToLongId(projectRepository.findById(id).orElse(null));
    }

    public ProjectFull fullInfo(Long id) throws InvalidIdentifierException {
        if(!projectRepository.existsById(id))
            throw new InvalidIdentifierException("Project", id);
        return projectMapper.mapToFull(projectRepository.fullInformation(id).orElse(null));
    }

    public long getTasksCount(Long id){
        return projectRepository.tasksCount(id);
    }

    public void updateAllProjects(ProjectLongId[] projectsLongId, boolean override) throws InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException {
        if(override) {
            for (ProjectLongId projectLongId : projectsLongId)
                overrideProjects(projectLongId);
        }
        else {
            for (ProjectLongId projectLongId : projectsLongId)
                updateProject(projectLongId);
        }
    }

    public void updateProject(ProjectLongId projectLongId) throws InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException {
        Long id = projectLongId.getId();
        if(id == null || !projectRepository.existsById(id))
            throw new InvalidIdentifierException("Project", id);
        Project newProject = projectRepository.findById(id).orElse(null);
        Project update = projectMapper.mapToEntity(projectLongId);
        if (update.getDescription() != null)
            newProject.setDescription(update.getDescription());
        projectRepository.save(newProject);
    }

    public void overrideProjects(ProjectLongId projectLongId) throws InvalidIdentifierException, NoSuchFieldException, RequiredFieldMissingException {
        Long id = projectLongId.getId();
        if(id == null || !projectRepository.existsById(id))
            throw new InvalidIdentifierException("Project", id);
        Project update = projectMapper.mapToEntity(projectLongId);
        projectRepository.save(update);
    }

    public void deleteAllProjects(){
        projectRepository.deleteAll();
    }

    public void deleteProjectById(Long id) throws InvalidIdentifierException {
        if(!projectRepository.existsById(id))
            throw new InvalidIdentifierException("Project", id);
        projectRepository.deleteById(id);
    }
}
