package fr.mahery.preface.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fr.mahery.preface.entities.Address;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@SuppressWarnings("serial")
@Entity
public class User implements Serializable{
	
	@Id @GeneratedValue
	private long id;
	@NotNull @Column(unique = true)
	private String username;
	@NotNull
	@Size(min=5, max=15)
	private String password;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull @Column(unique = true)
	private String mailAddress;
	@NotNull @Column(unique = true)
	private String mobilePhoneNumber;
	
	private String homePhoneNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Address address;
	
	public User() {
		super();
	}

	public long getId() {
		return id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}

	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}
	
	
}
