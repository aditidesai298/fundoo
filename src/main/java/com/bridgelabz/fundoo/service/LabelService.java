package com.bridgelabz.fundoo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.LabelDto;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.LabelRepository;
import com.bridgelabz.fundoo.repository.UserRepository;

import com.bridgelabz.fundoo.util.JwtGenerator;

@Service
public class LabelService implements ILabelService {
	@Autowired
	private JwtGenerator jwt;

	@Autowired
	private UserRepository urepo;

	@Autowired
	private LabelRepository lrepo;

	@Override
	public int createLabel(LabelDto ldto, String token) {
		long userId = jwt.decodeToken(token);
		User isUserAvailable = urepo.getUser(userId);
		if (isUserAvailable != null) {
			String labelname = ldto.getLabelTitle();
			Label label = lrepo.findByName(labelname);
			if (label == null) {
				return lrepo.insertLabelData(ldto.getLabelTitle(), userId);

			}
		}
		return 0;
	}
}