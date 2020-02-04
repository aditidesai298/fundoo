package com.bridgelabz.fundoo.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.LabelException;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.LabelDto;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.ILabelRepository;
import com.bridgelabz.fundoo.repository.INoteRepository;
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
	@Autowired
	private INoteRepository nRepo;

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
		throw new LabelException("Label already exists", 208);

	}
	
	@Override
	public boolean createLabelAndMap(String token, long noteId, LabelDto labelDTO) {
		User fetchedUser = uRepo.getUser(jwt.decodeToken(token));
		Note fetchedNote = nRepo.getNote(noteId);
		Label fetchedLabel = lRepo.findOneBylabelName(labelDTO.getLabelName());
		if (fetchedLabel == null) {
			Label newLabel = new Label();
			BeanUtils.copyProperties(labelDTO, newLabel);
			
			fetchedUser.getLabels().add(newLabel);
			fetchedNote.getLabelsList().add(newLabel);
			lRepo.save(newLabel);
			return true;
		}
		throw new LabelException("Label already exists", 208);
	}
	@Override
	public boolean isLabelEdited(String token, LabelDto labelDTO, long labelId) {
		uRepo.getUser(jwt.decodeToken(token));
		Optional<Label> fetchedLabel = lRepo.findById(labelId);
		if (fetchedLabel.isPresent()) {
			if (isValidNameForEdit(fetchedLabel, labelDTO)) {
				lRepo.updateLabelName(labelDTO.getLabelName(), fetchedLabel.get().getLabelId());
				return true;
			}
			return false;
		}
		throw new LabelException("Label not found", 400);
	}

	private boolean isValidNameForEdit(Optional<Label> fetchedLabel, LabelDto labelDTO) {

		if (lRepo.checkLabelWithDb(labelDTO.getLabelName()).isEmpty()) {
			return !fetchedLabel.get().getLabelName().equals(labelDTO.getLabelName());
		}
		throw new LabelException("name with the given label already exist in your account",
				400);
	}

}