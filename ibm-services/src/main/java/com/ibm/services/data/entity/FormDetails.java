package com.ibm.services.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="form_details")
public class FormDetails {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator 
	(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	private String id;
	private String formId;
	private String formStatus;
	private String requestorName;
	private String requestorId;
	private String riskRating;
	private String datePrepared;
	private String validTo;
	private String salesConnectNo;
	private String rfsNo;
	private String region;
	private String coe;
	private String customerName;
	private String customerId;
	private String salesId;
	private String approver;
	private String custom;
	@OneToOne (cascade=CascadeType.ALL)
	private EstimatorDetails estimatorDetails;
	@OneToOne (cascade=CascadeType.ALL)
	private ServerDetails serverDetails;
	@OneToOne (cascade=CascadeType.ALL)
	private WorkstationDetails workstationDetails;
	
}
