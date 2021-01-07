package com.spring.batch.csv.json.springbatchcsvtojson.model;

import java.io.Serializable;

public class Email implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String mailId;

	public Email(final String mailId) {
		this.mailId = mailId;

	}
	
	public String toString() {
		return this.mailId;
	}

}
