package com.bridgelabz.fundoo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.bridgelabz.exception.UserException;
import com.bridgelabz.fundoo.exception.AuthorizationException;
import com.bridgelabz.fundoo.exception.LabelException;
import com.bridgelabz.fundoo.exception.NoteException;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.LabelDto;
//import com.bridgelabz.model.LabelUpdate;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.ILabelRepository;
import com.bridgelabz.fundoo.repository.INoteRepository;
import com.bridgelabz.fundoo.repository.IUserRepository;
//import com.bridgelabz.fundoo.repository.UserRepositoryImplementation;
import com.bridgelabz.fundoo.util.JwtGenerator;

@Service
public class LabelService implements ILabelService {
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private ILabelRepository labelRepository;
	@Autowired
	private INoteRepository noteRepository;
	@Autowired
	private JwtGenerator jwtToken;

	private User authenticatedUser(String token) {
		User fetchedUser = userRepository.getUser(jwtToken.decodeToken(token));
		if (fetchedUser != null) {
			return fetchedUser;
		}
		throw new AuthorizationException("USER authorization exception",
				400);
	}


	private Note verifiedNote(long noteId) {
		Note fetchedNote = noteRepository.getNote(noteId);
		if (fetchedNote != null) {
			return fetchedNote;
		}
		throw new NoteException("Not found", 400);
	}

	@Override
	public void createLabel(LabelDto labelDTO,String token) {
		User fetchedUser = authenticatedUser(token);
		Label fetchedLabel = labelRepository.findOneBylabelName(labelDTO.getName());
		if (fetchedLabel != null) {
			throw new LabelException("already exists",
					400);
		}
		Label newLabel = new Label();
		BeanUtils.copyProperties(labelDTO, newLabel);
		
		fetchedUser.getLabels().add(newLabel);
		labelRepository.save(newLabel);
	}
}