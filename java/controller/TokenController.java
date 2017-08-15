package controller;

import dbmanager.ConnectionManager;
import dao.TokenDAO;
import model.Token;
import java.sql.Connection;
import java.time.LocalDateTime;
import security.SaltRandom;

public class TokenController {
	private Token token;
	private TokenDAO tokendao;
	private ConnectionManager mn;
	private Connection connection;

	public TokenController() {
		mn = new ConnectionManager();
		tokendao = new TokenDAO(mn.getConnection());
	}

	public void insert() {
		token = new Token();
		SaltRandom salt = new SaltRandom();
		LocalDateTime expDate = LocalDateTime.now().plusHours(10);
		token.setToken(salt.SaltRandom());
		token.setExpDate(expDate);
		tokendao.insertToken(token);
	}

	public void delete() {
		tokendao.deleteToken();
	}

	public boolean iswezig() {
		return tokendao.wezig();
	}

	public Token getToken() {
		return tokendao.getToken();
	}

}
