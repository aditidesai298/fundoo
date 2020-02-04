package com.bridgelabz.fundoo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.LabelException;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.LabelDto;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.ILabelRepository;
import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.util.JwtGenerator;

@Service
public class LabelService implements ILabelService {
	@Autowired
	private IUserRepository uRepo;
	@Autowired
	private ILabelRepository lRepo;
	@Autowired
	private JwtGenerator jwt;

	@Override
	public void createLabel(String token, LabelDto labelDTO) {
		User fetchedUser = uRepo.getUser(jwt.decodeToken(token));
		Label fetchedLabel = lRepo.findOneBylabelName(labelDTO.getLabelName());
		
		if (fetchedLabel == null) {
			Label newLabel = new Label();
			BeanUtils.copyProperties(labelDTO, newLabel);
			fetchedUser.getLabels().add(newLabel);
			lRepo.save(newLabel);
			return;
		}
		throw new LabelException("Label already exists", 400);

	}

}