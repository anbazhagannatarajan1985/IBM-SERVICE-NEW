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
@Table(name="workstation_details")
public class WorkstationDetails {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator 
	(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	private String Id;
	private String vendor;
	private String antiMalwareAgent;
	private Boolean hostIpsOnServers;
	private Boolean hostFirewallOnServers;
	private Boolean deviceApplicationControl;
	private String noOfConsoles;
	private String serverFactor;
	private String serverSupported;
	private String encryptionEffort;
	private String hipsEffort;
	private String hipsFwEffort;
	private String deviceApplicationControlEffort;
	private String noOfConsoleEffort;
	private String fteRoundOff;
	private String fteForCalculation;
	private String serverFactorBaseFte;
	private String serverSupportedBaseFte;
	private String encryptionEffortBaseFte;
	private String hipsEffortBaseFte;
	private String hipsFwEffortBaseFte;
	private String deviceApplicationControlBaseFte;
	private String noOfConsoleEffortBaseFte;
	private String fteRoundOffBaseFte;
	private String fteForCalculationBaseFte;
	private String serverFactorFinalFte;
	private String serverSupportedFinalFte;
	private String hipsEffortFinalFte;
	private String hipsFwEffortFinalFte;
	private String deviceApplicationControlFinalFte;
	private String noOfConsoleEffortFinalFte;
	private String fteRoundOffFinalFte;
	private String fteForCalculationFinalFte;

}
