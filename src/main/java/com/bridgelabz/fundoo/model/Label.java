package com.bridgelabz.fundoo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "label")
public class Label {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long labelId;
	
	@NotBlank
	private String labelTitle;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User userLabel;

	@ManyToMany(mappedBy = "labels")
	private List<Note> listnote;
	
	public Label() 
	{
		
	}

	public Label(long labelId, @NotBlank String labelTitle) {
		super();
		this.labelId = labelId;
		this.labelTitle = labelTitle;
	}

	public Label(long labelId, @NotBlank String labelTitle, User userLabel, List<Note> listnote) {
		super();
		this.labelId = labelId;
		this.labelTitle = labelTitle;
		this.userLabel = userLabel;
		this.listnote = listnote;
	}

	public long getLabelId() {
		return labelId;
	}

	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}

	public String getLabelTitle() {
		return labelTitle;
	}

	public void setLabelTitle(String labelTitle) {
		this.labelTitle = labelTitle;
	}

	public User getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(User userLabel) {
		this.userLabel = userLabel;
	}

	public List<Note> getListnote() {
		return listnote;
	}

	public void setListnote(List<Note> listnote) {
		this.listnote = listnote;
	}

}