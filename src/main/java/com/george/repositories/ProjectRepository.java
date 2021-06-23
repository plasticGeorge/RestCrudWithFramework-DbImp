package com.george.repositories;

import com.george.entities.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    @Query(value = "SELECT * FROM PROJECT p LEFT JOIN USER u ON p.PROJECT_ID = u.PROJECT_ID " +
                    "LEFT JOIN TASK t ON u.USER_ID = t.TASK_ID WHERE p.PROJECT_ID = :id",
            nativeQuery = true)
    Optional<Project> fullInformation(@Param("id") long id);

    @Query(value = "SELECT COUNT(DISTINCT u.USER_ID) FROM PROJECT p " +
            "LEFT JOIN USER u ON p.PROJECT_ID = u.PROJECT_ID WHERE p.PROJECT_ID = :id",
            nativeQuery = true)
    Long tasksCount(@Param("id") long id);
}
