package com.ibm.services.data.manager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;

import com.ibm.services.data.entity.FormDetails;
import com.ibm.services.data.entity.Mail;
import com.ibm.services.data.entity.Vendor;
import com.ibm.services.data.repo.IFormRepo;
import com.ibm.services.data.repo.IVendorRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FormManager {
	
	@Autowired
	IFormRepo formRepo;
	
	@Autowired
	IVendorRepo vendorRepo;
	@Autowired
	MailManager mailManager;
	
	public Boolean createForm(FormDetails accountDetails) {
		try {
			formRepo.save(accountDetails);
			sendApproverMail(accountDetails);
		} catch (Exception e) {
			log.error("Failed to create Form for : " + accountDetails.getFormId() + " : " + e.getMessage());
			return false;
		}
		
		return true;
			
	}
	
	public List<FormDetails> getAllFormsByApproverAndStatus(String approver, String status) {
		return formRepo.findAllByApproverAndFormStatusOrderByFormId(approver, status);
	}
	
	public List<FormDetails> getAllFormsByRequestor(String requestor) {
		return formRepo.findAllByRequestorIdOrderByFormId(requestor);
	}
	
	/**
	 * get all the vendors
	 * 
	 * @return
	 */
	public List<Vendor> findAllVendors() {
		return vendorRepo.findAll();
	}
	
	public FormDetails findByFormId(String formId) {
		return formRepo.findByFormId(formId);
	}
	
	public Boolean updateFormApproved(String formId) {
		try {
		formRepo.updateFormAsApproved("3", formId);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	private void sendApproverMail(FormDetails formDetails) throws IOException, MessagingException {
		
		ByteArrayInputStream generateSecurityReport = PdfManager.GenerateSecurityReport(formDetails);
		byte[] array = new byte[generateSecurityReport.available()];
		generateSecurityReport.read(array);
		InputStreamSource attachment = new ByteArrayResource(array);
		
		Mail mail = new Mail();
		mail.setFormId(formDetails.getFormId());
		mail.setApproverEmail(formDetails.getApprover());
		mail.setIsHtml(true);
		mail.setRequestorEmail(formDetails.getRequestorId());
		mail.setSubject("IBM Security Form - Pending Approval");
		List<String> toList = new ArrayList<>();
		toList.add(mail.getApproverEmail());
		
		mail.setToList(toList);
		
		mail.setAttachment(attachment);
		
		mailManager.sendMail(mail);
		
	}

}
