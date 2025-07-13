package com.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.entity.Task;

public interface TaskRepository extends JpaRepository <Task, String>{
	
	List<Task> findByStatus(Task.Status status);

}
