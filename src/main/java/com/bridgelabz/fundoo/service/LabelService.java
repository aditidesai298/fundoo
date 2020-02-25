package com.bridgelabz.fundoo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.dto.LabelDto;
import com.bridgelabz.fundoo.exception.LabelException;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.ILabelRepository;
import com.bridgelabz.fundoo.repository.INoteRepository;
import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.util.JwtGenerator;

/**
 * The class that contains the services for label controller such as creating a
 * label, deleting a label, etc. This class contains the implementation of all
 * such methods for the Controller to use.
 * 
 * @author Aditi Desai
 * @version 1.0
 * @created 2.2.20
 */
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
	public void createLabel(String token, LabelDto lDto) {
		
		User fetchedUser = uRepo.getUser(jwt.decodeToken(token));
		Label fetchedLabel = lRepo.findOneBylabelName(lDto.getLabelName());

		if (fetchedLabel == null) {
			Label newLabel = new Label();
			BeanUtils.copyProperties(lDto, newLabel);
			fetchedUser.getLabels().add(newLabel);
			lRepo.save(newLabel);
			return;
		}
		throw new LabelException("Label already exists", 208);

	}

	@Override
	public boolean createLabelAndMap(String token, long noteId, LabelDto lDTO) {
		
		User fetchedUser = uRepo.getUser(jwt.decodeToken(token));
		Note fetchedNote = nRepo.getNote(noteId);
		Label fetchedLabel = lRepo.findOneBylabelName(lDTO.getLabelName());
		
		if (fetchedLabel == null) {
			Label newLabel = new Label();
			BeanUtils.copyProperties(lDTO, newLabel);

			fetchedUser.getLabels().add(newLabel);
			fetchedNote.getLabelsList().add(newLabel);
			lRepo.save(newLabel);
			return true;
		}
		throw new LabelException("Label already exists", 208);
	}

	@Override
	public boolean editLabel(String token, LabelDto lDto, long lId) {
		
		uRepo.getUser(jwt.decodeToken(token));
		Optional<Label> fetchedLabel = lRepo.findById(lId);
		
		if (fetchedLabel.isPresent()) {
			if (lRepo.checkLabelWithDb(lDto.getLabelName()).isEmpty()) {
				lRepo.updateLabelName(lDto.getLabelName(), fetchedLabel.get().getLabelId());
				return true;
			}
			return false;
		}
		throw new LabelException("Label not found", 400);
	}

	@Override
	public boolean deleteLabel(String token, long lId) {
		
		uRepo.getUser(jwt.decodeToken(token));
		Optional<Label> fetchedLabel = lRepo.findById(lId);
		if (fetchedLabel.isPresent()) {
			lRepo.delete(fetchedLabel.get());
			return true;
		}
		throw new LabelException("Label not found", 400);
	}

	@Override
	public List<Label> foundLabelsList(String token) {
		
		uRepo.getUser(jwt.decodeToken(token));
		return lRepo.getAllLabels();
	}

	@Override
	public boolean addNoteLabel(String token, long noteId, long labelId) {
		
		uRepo.getUser(jwt.decodeToken(token));
		Note fetchedNote = nRepo.getNote(noteId);
		Optional<Label> fetchedLabel = lRepo.findById(labelId);
		if (fetchedLabel.isPresent()) {
			fetchedNote.getLabelsList().add(fetchedLabel.get());
			lRepo.save(fetchedLabel.get());
			return true;
		}
		throw new LabelException("Label already exists", 400);
	}

	@Override
	public boolean remoNoteLabel(String token, long noteId, long labelId) {
		
		uRepo.getUser(jwt.decodeToken(token));
		Note fetchedNote = nRepo.getNote(noteId);
		Optional<Label> fetchedLabel = lRepo.findById(labelId);
		if (fetchedLabel.isPresent()) {
			fetchedNote.getLabelsList().remove(fetchedLabel.get());
			nRepo.saveOrUpdate(fetchedNote);
			return true;
		}
		throw new LabelException("label not found", 400);
	}

}