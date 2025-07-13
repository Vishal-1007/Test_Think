package com.test.entity;

import java.time.LocalDateTime;

import ch.qos.logback.core.status.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

public class Task {
	@Id
	
	private String taskStrId;
	
	private String description;
	 private int estimatedTimeMinutes;
	 
	 @Enumerated(EnumType.STRING)
	 
	 private Status status;
	 
	 private LocalDateTime submittedAt;
	 
	 public enum  Status{
		 PENDING,
		 PROCESSING,
		 COMPLETED,
	 }

	public String getTaskStrId() {
		return taskStrId;
	}

	public void setTaskStrId(String taskStrId) {
		this.taskStrId = taskStrId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getEstimatedTimeMinutes() {
		return estimatedTimeMinutes;
	}

	public void setEstimatedTimeMinutes(int estimatedTimeMinutes) {
		this.estimatedTimeMinutes = estimatedTimeMinutes;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(LocalDateTime submittedAt) {
		this.submittedAt = submittedAt;
	}
	 
	 
	 
	 
	 

}
