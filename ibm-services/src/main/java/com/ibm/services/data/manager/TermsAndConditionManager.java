package com.ibm.services.data.manager;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibm.services.data.entity.TermsAndCondition;
import com.ibm.services.data.repo.ITermsAndConditionRepo;

@Component
public class TermsAndConditionManager {
	
	@Autowired
	ITermsAndConditionRepo termsAndConditionRepo;
	
	public TermsAndCondition createTermsAndConditions(TermsAndCondition termsAndCondition) {
		Long id = termsAndCondition.getId();
		Integer currentVersion = 0;
		if(id != null) {
			Optional<TermsAndCondition> options = termsAndConditionRepo.findById(id);
			if (options.isPresent()) {
				TermsAndCondition existingTerm = options.get();
				existingTerm.setStatus(false);
				termsAndConditionRepo.save(existingTerm);
				currentVersion = existingTerm.getVersionNumber();
			}
		}
			
		termsAndCondition.setId(null);
		termsAndCondition.setStatus(true);
		termsAndCondition.setVersionNumber(currentVersion + 1);
		termsAndCondition.setCreatedDate(new Date(System.currentTimeMillis()));
		termsAndConditionRepo.save(termsAndCondition);
		return termsAndCondition;
		
	}
	
	public TermsAndCondition getTermsAndConditionByStatus() {
		return termsAndConditionRepo.findByStatus(true);
	}

}
