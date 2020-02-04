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
public interface LabelRepository extends JpaRepository<Label, Long> {

	@Query(value = "select * from label where label_title = :labelname", nativeQuery = true)
	Label findByName(String labelname);

	@Modifying
	@Query(value = "insert into label (label_title, user_id) values (:labelTitle, :userId)", nativeQuery = true)
	int insertLabelData(String labelTitle, long userId);

	@Query(value = "select * from label  where label_title = :labelTitle", nativeQuery = true)
	Label findByTitle(String labelTitle);

	@Query(value = "select * from label where label_id = :labelId and user_id =:userId", nativeQuery = true)
	Label findById(long labelId, long userId);

	@Modifying
	@Query(value = "update label set label_title = :labelTitle where label_id = :labelId", nativeQuery = true)
	void update(String labelTitle, long labelId);

	@Modifying
	@Query(value = "delete from label where user_id = :userId and label_id = :labelId", nativeQuery = true)
	void delete(long userId, long labelId);

	@Query(value = "select * from label where user_id = :userId", nativeQuery = true)
	List<Label> getall(long userId);

	@Modifying
	@Query(value = "insert into note_labels(listnote_id ,  labels_label_id)  values(:noteid, :labelId)", nativeQuery = true)
	void insertdatatomap(long noteid, long labelId);

	@Query(value = "select * from  note_labels where listnote_id = :noteid and labels_label_id =:labelId", nativeQuery = true)
	Object findoneByLabelIdAndNoteId(long labelId, long noteid);
	
}