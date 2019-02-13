package com.ibm.services.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="estimator_details")
public class EstimatorDetails {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator 
	(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	private String id;
	private String endPointSecurity;
	private String serviceWindow;
	private String serviceScope;
	private String b5;
	private String b7;
	private String b71;
	private String baseFte;
	private String totalFte;
	private String transitionFte;
	
}
