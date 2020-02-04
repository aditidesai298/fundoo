package com.bridgelabz.fundoo.service;

import com.bridgelabz.fundoo.model.LabelDto;

public interface ILabelService {
	
	int createLabel(LabelDto labeldto, String token);

}
