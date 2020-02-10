package com.bridgelabz.fundoo.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "note")
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private long n_id;
	private String title;
	private String description;
	private boolean isArchived;
	private boolean isPinned;
	private boolean isTrashed;
	private String color;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private LocalDateTime reminderDate;

	@ManyToMany
	@JoinTable(name = "note_label", joinColumns = { @JoinColumn(name = "n_id") }, inverseJoinColumns = {
			@JoinColumn(name = "l_id") })
	@JsonIgnore
	private List<Label> labelsList;

	@JsonIgnore
	@ManyToMany(mappedBy = "collaboratedNotes")
	private List<User> collaboratedUsers;

	public long getN_id() {
		return n_id;
	}

	public void setN_id(long n_id) {
		this.n_id = n_id;
	}

	public LocalDateTime getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(LocalDateTime reminderDate) {
		this.reminderDate = reminderDate;
	}

	public List<User> getCollaboratedUsers() {
		return collaboratedUsers;
	}

	public void setCollaboratedUsers(List<User> collaboratedUsers) {
		this.collaboratedUsers = collaboratedUsers;
	}

	public long getId() {
		return n_id;
	}

	public void setId(long noteId) {
		this.n_id = noteId;
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

	public void setRemainderDate(LocalDateTime reminderDate) {
		this.reminderDate = reminderDate;
	}

	public List<Label> getLabelsList() {
		return labelsList;
	}

	public void setLabelsList(List<Label> labelsList) {
		this.labelsList = labelsList;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + n_id + ", title=" + title + ", description=" + description + ", isArchived="
				+ isArchived + ", isPinned=" + isPinned + ", isTrashed=" + isTrashed + ", color=" + color
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", remainderDate=" + reminderDate
				+ "]";
	}

}