package com.bridgelabz.fundoo.service;

import java.util.List;

import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.LabelDto;

public interface ILabelService {

	public void createLabel(String token, LabelDto labelDTO);

	public boolean createLabelAndMap(String token, long noteId, LabelDto labelDTO);

	boolean editLabel(String token, LabelDto labelDTO, long labelId);

	boolean deleteLabel(String token, long labelId);

	List<Label> foundLabelsList(String token);

	boolean addNoteLabel(String token, long noteId, long labelId);

}
