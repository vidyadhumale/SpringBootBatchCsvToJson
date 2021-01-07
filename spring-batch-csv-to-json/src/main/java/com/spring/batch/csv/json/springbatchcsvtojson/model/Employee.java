package com.spring.batch.csv.json.springbatchcsvtojson.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "first_name", "last_name", "company_name" })
public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("company_name")
	private String companyName;

	private String address;
	private String city;
	private String country;
	private String state;
	private String zip;
	private PhoneNumber phone1;
	private PhoneNumber phone2;
	private Email email;
	private String web;

	public Employee() {
	}

	public Employee(String firstName, String lastName, String companyName, String address, String city, String country,
			String state, String zip, PhoneNumber phone1, PhoneNumber phone2, Email email, String web) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.companyName = companyName;
		this.address = address;
		this.city = city;
		this.country = country;
		this.state = state;
		this.zip = zip;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.email = email;
		this.web = web;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone1() {
		return phone1.toString();
	}

	public void setPhone1(PhoneNumber phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2.toString();
	}

	public void setPhone2(PhoneNumber phone2) {
		this.phone2 = phone2;
	}

	public String getEmail() {
		return email.toString();
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Employee [firstName=" + firstName + ", lastName=" + lastName + ", companyName=" + companyName
				+ ", address=" + address + ", city=" + city + ", country=" + country + ", state=" + state + ", zip="
				+ zip + ", phone1=" + phone1 + ", phone2=" + phone2 + ", email=" + email + ", web=" + web + "]";
	}

}
