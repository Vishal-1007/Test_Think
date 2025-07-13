package com.test.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.entity.Task;
import com.test.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	private TaskService service;
	
	
	@PostMapping
	public ResponseEntity<?> create (@RequestBody Task task){
		
		if(task.getEstimatedTimeMinutes() <=0)
			return ResponseEntity.badRequest().body("Estimated Time should be greater than 0");
		return ResponseEntity.ok(service.createtask(task));
		
	}
	
	@GetMapping("/{task_str_id}")
	
	public ResponseEntity<?> getTask(@PathVariable String task_str_id){
		Task task = service.getTask(task_str_id);
		return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{task_str_id}/status")
	
	public ResponseEntity<Task> updatedStatus(
			@PathVariable String task_str_id,
			@RequestBody String status) {
		try {
			Task.Status newStatus = Task.Status.valueOf(status.toUpperCase());
			Task updated = service.updateStatus(task_str_id, newStatus);
			if(updated != null) {
				return ResponseEntity.ok(updated);
			}
			else {
				return ResponseEntity.notFound().build();
			}
			
			
		}catch(IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(null);
		}
	
	}
			

	
	@GetMapping("/next-to-process")
	public ResponseEntity<?> getNext(){
		Task task = service.getNextTask();
		return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
		
	}
	
	@GetMapping("/pending")
	public ResponseEntity<List<Task>> listPending(
			@RequestParam(defaultValue = "submitted") String sortBy,
			@RequestParam(defaultValue = "asc") String order,
			@RequestParam(defaultValue = "10") int limit
			){
			return ResponseEntity.ok(service.getSortedPendingTasks(sortBy, order, limit));	
	}
}
