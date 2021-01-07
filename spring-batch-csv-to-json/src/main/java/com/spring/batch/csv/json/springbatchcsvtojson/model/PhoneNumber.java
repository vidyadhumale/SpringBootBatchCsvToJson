package com.spring.batch.csv.json.springbatchcsvtojson.model;

import java.io.Serializable;

public final class PhoneNumber implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Integer area; // area code (3 digits)
	private final Integer exch; // exchange (3 digits)
	private final Integer ext; // extension (4 digits)

	public PhoneNumber(int area, int exch, int ext) {
		this.area = area;
		this.exch = exch;
		this.ext = ext;
	}

	// 0 for padding with leading 0s
	public String toString() {
		return String.format("%03d-%03d-%04d", area, exch, ext);
	}

}
