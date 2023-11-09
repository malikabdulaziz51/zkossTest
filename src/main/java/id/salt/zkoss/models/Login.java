package id.salt.zkoss.models;

import jakarta.persistence.Column;
import jakarta.persistence.Table;


@Table(name = "Users")
public class Login {
	@Column(name = "Username")
	private String Username;
	@Column(name = "Password")
	private String Password;
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
}
