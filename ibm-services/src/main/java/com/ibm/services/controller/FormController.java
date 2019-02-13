package com.ibm.services.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Random;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ibm.services.data.entity.FormDetails;
import com.ibm.services.data.entity.Region;
import com.ibm.services.data.entity.TermsAndCondition;
import com.ibm.services.data.entity.Vendor;
import com.ibm.services.data.manager.FormManager;
import com.ibm.services.data.manager.PdfManager;
import com.ibm.services.data.manager.RegionManager;
import com.ibm.services.data.manager.TermsAndConditionManager;
import com.ibm.services.exception.UserNameAlreadyTakenException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(value = "account service", description = "A set of endpoints for managing form details")
@RestController
@RequestMapping("/form")
@Slf4j
public class FormController {
	
	@Autowired
	FormManager formManager;
	@Autowired
	RegionManager regionManager;
	@Autowired
	TermsAndConditionManager termsAndConditionManager;
	
	@ApiOperation(value = "createform", notes = "Create a new form")
    @RequestMapping(value = "/new-form", method = RequestMethod.PUT)
    public Boolean createForm(@RequestBody FormDetails formDetails) throws UserNameAlreadyTakenException {
        return formManager.createForm(formDetails);
    }
	
	@ApiOperation(value = "getForm", notes = "get form by form id")
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public FormDetails getForm(@RequestParam("f") String formId) throws UserNameAlreadyTakenException {
        return formManager.findByFormId(formId);
    }
	
	@ApiOperation(value = "regionList", notes = "get all region")
    @RequestMapping(value = "/regions", method = RequestMethod.GET)
    public List<Region> getRegionsList() {
        return regionManager.findAll();
    }
	
	@ApiOperation(value = "newformid", notes = "get new form id")
    @RequestMapping(value = "/new-form-id", method = RequestMethod.GET)
    public Integer getNextFormId() {
		return new Random().nextInt();
    }
	
	@ApiOperation(value = "findAllvendor", notes = "get vendor list")
    @RequestMapping(value = "/vendors", method = RequestMethod.GET)
    public List<Vendor> getAllVendors() {
		return formManager.findAllVendors();
    }
	
	@ApiOperation(value = "download PDF", notes = "download pdf")
    @RequestMapping(value = "/download-form", method = RequestMethod.GET)
    public ResponseEntity<Object> downloadForm(@RequestParam("formId") String formId) {
		
		FormDetails findByFormId = formManager.findByFormId(formId);
		
		ByteArrayInputStream generateSecurityReport = PdfManager.GenerateSecurityReport(findByFormId);
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Disposition", "attachment; filename=123d.pdf");
		
		return ResponseEntity.ok().headers(header).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(generateSecurityReport));
		
    }
	
	
	@ApiOperation(value = "approveForm", notes = "approve the pending form")
    @RequestMapping(value = "/approve", method = RequestMethod.PUT)
    public StatusObject approveForm(@RequestParam("a") String approverEmail, @RequestParam("f") String formId) {
		 FormDetails formDetails = formManager.findByFormId(formId);
		 Boolean isUpdated = false;
		 if (formDetails != null) {
			 isUpdated = formManager.updateFormApproved(formId);
		 }
		 StatusObject statusObj = new StatusObject();
		 if(isUpdated) {
			 statusObj.setStatus("Approved");
		 } else {
			 statusObj.setStatus("Failed");
		 }
		 
		 return statusObj;
    }
	
	@ApiOperation(value = "terms and condition", notes = "terms and condition")
    @RequestMapping(value = "/term-condition", method = RequestMethod.PUT)
    public TermsAndCondition updateTermsAndConditions(@RequestBody TermsAndCondition termsAndCondition) {
		return termsAndConditionManager.createTermsAndConditions(termsAndCondition);
    }
	
	@ApiOperation(value = "terms and condition", notes = "terms and condition")
    @RequestMapping(value = "/term-condition", method = RequestMethod.GET)
    public TermsAndCondition getTermsAndConditions() {
		return termsAndConditionManager.getTermsAndConditionByStatus();
    }
	
	@ApiOperation(value = "pendingApprovalList", notes = "return all the forms which are waiting for approval")
    @RequestMapping(value = "/pending-approval", method = RequestMethod.GET)
    public List<FormDetails> getPendingApprovalList(@RequestParam("a") String approver, @RequestParam("s") String status) {
		return formManager.getAllFormsByApproverAndStatus(approver, status);
    }
	
	@ApiOperation(value = "pendingApprovalList", notes = "return all the forms which are waiting for approval")
    @RequestMapping(value = "/requestor-forms", method = RequestMethod.GET)
    public List<FormDetails> getFormListByRequestor(@RequestParam("r") String requestor) {
		return formManager.getAllFormsByRequestor(requestor);
    }
	
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	private class StatusObject {
		private String status;		
	}

	
	


}
