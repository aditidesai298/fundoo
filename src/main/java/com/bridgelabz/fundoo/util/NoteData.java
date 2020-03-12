package com.bridgelabz.fundoo.util;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import lombok.Data;
@Entity
@Component
@Data
public class NoteData {
	
	@Id
	private long n_id;
	@Column
	private long id;

	@Column

	private String title;
	@Column

	private String description;

	@Column

	private boolean is_archived;
	@Column

	private boolean is_pinned;
	@Column

	private boolean is_trashed;
	@Column

	private String color;
	@Column

	private LocalDateTime created_date;
	@Column

	private LocalDateTime updated_date;
	@Column

	private LocalDateTime reminder_date;
}
