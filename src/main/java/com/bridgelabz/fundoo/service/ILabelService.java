package com.bridgelabz.fundoo.service;

import com.bridgelabz.fundoo.model.LabelDto;

public interface ILabelService {

	public void createLabel(String token, LabelDto labelDTO);

	public boolean createLabelAndMap(String token, long noteId, LabelDto labelDTO);

	boolean editLabel(String token, LabelDto labelDTO, long labelId);

	boolean deleteLabel(String token, long labelId);

}
