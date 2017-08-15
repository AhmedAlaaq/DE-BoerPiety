package dao;

import exception.*;
import java.sql.*;
import dbmanager.*;
import model.Token;
import java.time.LocalDateTime;

public class TokenDAO {

	private Connection connection;
	private Token token;

	public TokenDAO(Connection connection) {
		this.connection = connection;

	}

	public void insertToken(Token token) {

		String str = " insert into Token (token, expDate) " + "values (?, ?);";
		try (PreparedStatement statement = connection.prepareStatement(str, Statement.RETURN_GENERATED_KEYS);) {
			statement.setString(1, token.getToken());
			String exp = token.getExpDate().toString();
			statement.setObject(2, exp);
			statement.executeUpdate();
			ResultSet res = statement.getGeneratedKeys();
			if (res.isBeforeFirst()) {
				res.next();
				token.setId(res.getInt(1));
				System.out.println("Het teovoeging van de token is geslaagd");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void deleteToken() {
		String str = "delete from Token;";
		try (PreparedStatement statement = connection.prepareStatement(str)) {
			statement.executeUpdate();
			System.out.println(" Het wissen van deze Token is geslagd ");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Token getToken() {
		Token token = null;
		String str = " select * from Token ;";
		try (PreparedStatement statement = connection.prepareStatement(str)) {
			ResultSet res = statement.executeQuery();
			if (res.next()) {
				token = new Token();
				token.setId(res.getInt(1));
				token.setToken(res.getString(2));
				LocalDateTime t = null;
				token.setExpDate(t.parse(res.getString(3)));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return token;
	}

	public boolean wezig() {
		boolean isWezig = false;
		String str = " select * from Token;";
		try (PreparedStatement statement = connection.prepareStatement(str)) {
			ResultSet res = statement.executeQuery();
			if (res.next())
				isWezig = true;
			else
				isWezig = false;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isWezig;
	}
}
