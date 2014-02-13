package com.taxi.pojos;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the user database table.
 * 
 */
public class UserPojo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private Date createDate;

	private String eMail;

	private String language;

	private String mobile;

	private Date modificationDate;

	private String name;

	private String note;

	private String password;

	private String photoURL;

	private byte statusID;

	private String surname;

	private String username;

	public UserPojo() {
	}

	public UserPojo(int id, Date createDate, String email, String language, String mobile, Date modificationDate, String name, String note, String password, String photoURL, byte statusID, String surname, String username) {
		
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getEMail() {
		return this.eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
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

	public byte getStatusID() {
		return this.statusID;
	}

	public void setStatusID(byte statusID) {
		this.statusID = statusID;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}