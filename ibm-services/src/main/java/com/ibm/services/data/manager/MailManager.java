package com.ibm.services.data.manager;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.ibm.services.data.entity.Mail;
import com.ibm.services.util.StringFunctions;

@Component
@Slf4j
public class MailManager {
	@Autowired
    private JavaMailSender sender;
	
	@Value("${issue.mail.cc}")
	private String[] ccList;
	
	@Value("${aproval.service.url}")
	private String serviceUrl;
	
	@Value("${spring.mail.host}")
	private String mailHost;
	
	@Autowired
    public MailManager(JavaMailSender sender) {
        this.sender = sender;
    }
	
	public Boolean sendMail() {
		String[] toList = {"anbazhagan.natarajan45105@gmail.com", "Ashok.natar@gmail.com"};
		Mail mail = new Mail();
//		mail.setChangedBy("Anbazhagan Natarajan");
//		mail.setChangedByEmail("Anbazhagan.Natarajan1985@gmail.com");
//		mail.setComment("This is test comment");
//		mail.setCurrentResolution("Closed");
//		mail.setCurrentStatus("In Development");
//		mail.setOldStatus("Open");
//		mail.setIsHtml(true);
//		mail.setNewPlatform("NeedTo");
//		mail.setSummary("Duplicate options are available in THROQ");
		mail.setSubject("IBM Security Form - Pending Approval.");
//		mail.setTicketNo("NT-123");
//		mail.setIsUpdate(false);
		
		mail.setContent(this.prepareHtmlContent(mail));
		return this.sendMail(mail.getSubject(), toList, mail.getContent(), mail.getIsHtml());
	}
	
	public Boolean sendMail(String subject,  String[] toList, String content, Boolean isHtml ) {
		 MimeMessage message = sender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);

	        try {
	        	helper.setFrom("IBM Security");
	            helper.setTo(toList);
	            if (ccList != null && ccList.length > 0) {
	            	helper.setCc(ccList);
	            }
	            helper.setText(content, isHtml);
	            helper.setSubject(subject);
	        } catch (MessagingException e) {
	            e.printStackTrace();
	            return false;
	        }
	        sender.send(message);
	        return true;
	}
	
	public void sendMail(Mail mail) throws MessagingException {
		log.debug("send mail called for : " + mail.getFormId() );
		if (StringFunctions.isNotEmpty(mailHost)) {
			log.debug("Send mail started");
			String content = prepareHtmlContent(mail);
			MimeMessage message = sender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);

	        try {
	        	if (mail.getToList() != null) {
	        		helper.setTo(mail.getToList().toArray(new String[0]));
	        	}
	        	if (ccList != null && ccList.length > 0) {
	        		helper.setCc(ccList);
	        	}
	            helper.setText(content, mail.getIsHtml());
	            helper.setSubject(mail.getSubject());
	            helper.addAttachment(mail.getFormId() + ".pdf", mail.getAttachment(), "application/pdf");
	        } catch (MessagingException e) {
	        	log.error("error while sending email for : " + mail.getFormId());
	        	log.error(e.getMessage());
	            e.printStackTrace();
	        }
	        
	        try {
				initMail(message);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		log.debug("email host is empty hence skipped send mail");
	        
	}
	
	public String prepareHtmlContent(Mail mail) {
		
		String html = "<div>\r\n" + 
				"<p> Form Id <b>"+mail.getFormId()+"</b> is waiting for your approval.</p>\r\n" + 
				"<br>\r\n" + 
				"<a href="+ serviceUrl + "?a=" + mail.getApproverEmail() + "&f=" + mail.getFormId() +  ">\r\n" + 
				"<button style=\"background-color:blue; color: white;\">Click here to Approve</button>\r\n" + 
				"</a>\r\n" + 
				"<div>";
		
		return html;
				
		
	}
	
	public void initMail(MimeMessage message) throws ServletException {
		Thread createEventThread = new Thread(new CreateEvent(message), "EmailService");
		createEventThread.start();
		
	}
	
	private class CreateEvent implements Runnable {
		private  MimeMessage message =  null;
		
		public CreateEvent(MimeMessage message) {
			this.message = message;
		}
		
		@Override
		public void run() {
			sender.send(message);
		}
	}

}
