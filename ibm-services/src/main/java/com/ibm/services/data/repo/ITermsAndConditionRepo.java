package com.ibm.services.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.services.data.entity.TermsAndCondition;

public interface ITermsAndConditionRepo extends JpaRepository<TermsAndCondition, Long> {

	public TermsAndCondition findByStatus(Boolean status);
}
