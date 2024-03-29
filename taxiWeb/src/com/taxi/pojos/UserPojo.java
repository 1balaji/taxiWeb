package com.taxi.pojos;

import java.util.Date;

/**
 * The persistent class for the user database table.
 * 
 */

public class UserPojo extends BasePojo  {

	private static final long serialVersionUID = 1L;
	
	private int id;

	private Date createDate;

	private String email;

	private String language;

	private String mobile;

	private Date modificationDate;

	private String name;

	private String note;

	private String password;

	private String photoURL;

	private int providerID;

	private String providerUserID;

	private int statusID;

	private String surname;

	private boolean  isConfirmed;

	private String username;
	

	public UserPojo() {
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getModificationDate() {
		return this.modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhotoURL() {
		return this.photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public int getProviderID() {
		return this.providerID;
	}

	public void setProviderID(int providerID) {
		this.providerID = providerID;
	}

	public String getProviderUserID() {
		return this.providerUserID;
	}

	public void setProviderUserID(String providerUserID) {
		this.providerUserID = providerUserID;
	}

	public int getStatusID() {
		return this.statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public boolean isConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}