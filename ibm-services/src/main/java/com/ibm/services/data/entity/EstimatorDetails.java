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
	private Integer b5Tnt;
	private Integer b7Tnt;
	private Integer b7OnCallTnt;
	private Integer totalTnt;
	private Integer b5Ssb;
	private Integer b7Ssb;
	private Integer b7OnCallSsb;
	private Integer totalSsb;
	private Integer b5Hour;
	private Integer b7Hour;
	private Integer b7OnCallHour;
	private Integer totalHour;
	private Double b5Cost;
	private Double b7Cost;
	private Double b7OnCallCost;
	private Double totalCost;

	
}
