package com.bridgelabz.fundoo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "label")
public class Label {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "l_id")
	private long l_Id;
	private String labelName;
	

	@JsonIgnore
	@ManyToMany(mappedBy = "labelsList",fetch = FetchType.LAZY)
	private List<Note> noteList;

	public long getLabelId() {
		return l_Id;
	}

	public void setLabelId(long labelId) {
		this.l_Id = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public List<Note> getNoteList() {
		return noteList;
	}

	public void setNoteList(List<Note> noteList) {
		this.noteList = noteList;
	}

	

}