package com.bridgelabz.fundoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.model.Label;

@Repository
public interface ILabelRepository extends JpaRepository<Label, Long> {

	public Label findOneBylabelName(String name);

	@Query(value = "select * from note_labes",nativeQuery = true)
	public Label check();

}