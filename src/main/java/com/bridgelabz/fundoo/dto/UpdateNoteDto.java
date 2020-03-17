package com.bridgelabz.fundoo.dto;

import java.time.LocalDateTime;

public class UpdateNoteDto {
	
	private int n_id;
	private String title;
	private String description;
	private boolean isArchived;
	private boolean isTrashed;
	private boolean isPinned;
	private LocalDateTime updatedtime;
	public int getN_id() {
		return n_id;
	}
	public void setN_id(int n_id) {
		this.n_id = n_id;
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
	public boolean isTrashed() {
		return isTrashed;
	}
	public void setTrashed(boolean isTrashed) {
		this.isTrashed = isTrashed;
	}
	public boolean isPinned() {
		return isPinned;
	}
	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}
	public LocalDateTime getUpdatedtime() {
		return updatedtime;
	}
	public void setUpdatedtime(LocalDateTime updatedtime) {
		this.updatedtime = updatedtime;
	}
	

	

}
