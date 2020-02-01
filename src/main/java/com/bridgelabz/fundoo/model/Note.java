package com.bridgelabz.fundoo.model;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component
@Entity
@Table(name = "note")
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long noteId;
	private String title;
	private String description;
	private boolean isArchived;
	private boolean isPinned;
	private boolean isTrashed;
	private String color;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private LocalDateTime reminderDate;


	public long getId() {
		return noteId;
	}

	public void setId(long noteId) {
		this.noteId = noteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}

	
	public boolean isArchived() {
		return isArchived;
	}

	
	public void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}

	
	public boolean isPinned() {
		return isPinned;
	}

	
	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	
	public boolean isTrashed() {
		return isTrashed;
	}

	
	public void setTrashed(boolean isTrashed) {
		this.isTrashed = isTrashed;
	}


	public String getColor() {
		return color;
	}

	
	public void setColor(String color) {
		this.color = color;
	}

	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}


	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	
	public LocalDateTime getRemainderDate() {
		return reminderDate;
	}

	public void setRemainderDate(LocalDateTime remainderDate) {
		this.reminderDate = remainderDate;
	}

	
	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", title=" + title + ", description=" + description + ", isArchived="
				+ isArchived + ", isPinned=" + isPinned + ", isTrashed=" + isTrashed + ", color=" + color
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", remainderDate=" + reminderDate
				+ "]";
	}

}