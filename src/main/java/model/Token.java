package model;

import java.time.LocalDateTime;

public class Token {
	int id;
	String token;
	LocalDateTime expDate;

	public Token() {

	}

	public Token(String token, LocalDateTime expDate) {
		setToken(token);
		setExpDate(expDate);

	}

	public Token(int id, String token, LocalDateTime expDate) {
		setId(id);
		setToken(token);
		setExpDate(expDate);

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpDate() {
		return expDate;
	}

	public void setExpDate(LocalDateTime expDate) {
		this.expDate = expDate;
	}
}