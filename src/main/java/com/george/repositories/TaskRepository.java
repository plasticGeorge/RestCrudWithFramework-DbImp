package com.george.repositories;

import com.george.entities.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    @Query(
            value = "SELECT * FROM TASK t WHERE t.TASK_ID = :id",
            nativeQuery = true)
    Optional<Task> fullInformation(@Param("id") long id);
}