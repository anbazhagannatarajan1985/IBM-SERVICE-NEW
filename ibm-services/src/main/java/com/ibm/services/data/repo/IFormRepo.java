package com.ibm.services.data.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ibm.services.data.entity.FormDetails;

public interface IFormRepo extends JpaRepository<FormDetails, String> {

	public FormDetails findByFormId(String formId);
	
	public List<FormDetails> findAllByApproverAndFormStatusOrderByFormId(String approver, String status);
	
	public List<FormDetails> findAllByRequestorIdOrderByFormId(String requestor);
		
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE Form_Details SET form_status = ?1 WHERE form_id=?2", nativeQuery=true)
	void updateFormAsApproved(String approved , String formId);
}
