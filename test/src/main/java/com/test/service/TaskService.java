package com.test.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.entity.Task;
import com.test.repository.TaskRepository;
@Service
public class TaskService {
	
	@Autowired
	
	private TaskRepository repo;
	
	
	public Task createtask (Task task) {
		task.setStatus(Task.Status.PENDING);
		task.setSubmittedAt(LocalDateTime.now());
		
		return repo.save(task);
		
	}
	
	public Task getTask(String id) {
		return repo.findById(id).orElse(null);
	}
	
	public Task updateStatus( String id, Task.Status newStatus) {
		Task task = getTask(id);
		if(task == null) 
			return null;
		
		if(task.getStatus() == Task.Status.COMPLETED && newStatus != Task.Status.COMPLETED) {
			throw new IllegalArgumentException("Cannot change the staus " + newStatus);
			
		}
		
		if(task.getStatus() == Task.Status.PROCESSING && newStatus != Task.Status.PENDING) {
			throw new IllegalArgumentException(" Cannot go back to Pending");
			
		}
		
		task.setStatus(newStatus);
		return repo.save(task);
	}
	
	public Task getNextTask() {
		return repo.findByStatus(Task.Status.PENDING).stream()
				.min(Comparator.comparing(Task::getEstimatedTimeMinutes)
						.thenComparing(Task::getSubmittedAt))
				.orElse(null);
	}
	
	public List<Task> getSortedPendingTasks(String sortBy, String order, int limit){
		List<Task> tasks = repo.findByStatus(Task.Status.PENDING);
		Comparator<Task> comparator = switch (sortBy) {
		case "time" -> Comparator.comparingInt(Task::getEstimatedTimeMinutes);
		case "submitted" -> Comparator.comparing(Task::getSubmittedAt);
		default -> Comparator.comparing(Task::getSubmittedAt);
		};
		
		if("desc".equalsIgnoreCase(order)) {
			comparator = comparator.reversed();
		}
		
		return tasks.stream().sorted(comparator).limit(limit).toList();
	}
	
	

}
