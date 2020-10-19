package com.leoncarraro.redditclone.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationEmail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String subject;
	private String recipient;
	private String body;
	
	public NotificationEmail() {
	}

	public NotificationEmail(String subject, String recipient, String body) {
		this.subject = subject;
		this.recipient = recipient;
		this.body = body;
	}
	
}
