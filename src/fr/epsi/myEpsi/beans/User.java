package fr.epsi.myEpsi.beans;

import java.sql.Timestamp;

public class User {

	private String id;
	private String password;
	private Boolean administrator;
	
	public User(){
		super();
	}
	
	public User(String id, String password, Boolean administrator) {
		super();
		this.id = id;
		this.password = password;
		this.administrator = administrator;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getAdministrator() {
		return administrator;
	}
	public void setAdministrator(Boolean administrator) {
		this.administrator = administrator;
	}
	
}
