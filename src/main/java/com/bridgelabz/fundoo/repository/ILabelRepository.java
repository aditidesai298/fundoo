package com.bridgelabz.fundoo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.model.Label;

@Repository
@Transactional
public interface ILabelRepository extends JpaRepository<Label, Long> {

	public Label findOneBylabelName(String name);

	@Modifying
	@Transactional
	@Query(value = " update label set label_name = ? where l_id = ?  ", nativeQuery = true)
	public void updateLabelName(String name, Long id);

	@Query(value = "select * from label where label_name = ?", nativeQuery = true)
	public List<Label> checkLabelWithDb(String labelName);
	

}