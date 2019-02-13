package com.ibm.services.data.entity;

import java.util.List;

import org.springframework.core.io.InputStreamSource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

	private String subject;
	private String content;
	private Boolean isHtml;
	private List<String> toList;
	private List<String> ccList;
	private String requestorName;
	private String requestorEmail;
	private String approverName;
	private String approverEmail;
	private String formId;
	private InputStreamSource attachment;
	
	private String url;
}
