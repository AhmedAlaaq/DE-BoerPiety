package dao;

///////////
import java.sql.*;
import java.util.Scanner;
import dbmanager.*;
import exception.updateException;
import model.Account;

////////////
public class AccountDAO {
	private ConnectionManager mn;
	private Connection connection;
	private Account account;

	public AccountDAO(Connection connection) {
		this.connection = connection;
	}

	/* INSERT DATA CLASS */
	public void InsertAccount(Account account) {

		String str = " insert into accounten (naam, wachtWoord, salt, hashP, accountStatus) " + "values (?, ?, ?, ?, ?);";
		try (PreparedStatement statement = connection.prepareStatement(str, Statement.RETURN_GENERATED_KEYS)) {

			statement.setString(1, account.getNaam());
			statement.setString(2, account.getWachtWoord());
			statement.setString(3, account.getSalt());
			statement.setString(4, account.getHashP());
			statement.setInt(5, account.getAccountStatus());
			statement.executeUpdate();
			ResultSet res = statement.getGeneratedKeys();
			if (res.isBeforeFirst()) {
				res.next();
				account.setId(res.getInt(1));
				System.out.println(" Het invullen van het niew account is geslagd ");
			}
		} catch (Exception ex) {
			throw new updateException("Het teovoeging van het account is gezakt");

		}
	}

	/* DE BIJWEREN KLASSE */
	public void UpDateAccountStatus(Account account) {

		String str = " update accounten set accountStatus = ? " + "where accounten.naam = ?;";
		try (PreparedStatement statement = connection.prepareStatement(str)) {
			statement.setInt(1, account.getAccountStatus());
			statement.setString(2, account.getNaam());
			statement.executeUpdate();
			System.out.println(" Het bijweken van het account is geslagd ");
		} catch (Exception ex) {
			throw new updateException("Het aanpassen van het account is gezakt");
		}
	}
	
	public void UpDateAccountWachtWoord(Account account) {

		String str = " update accounten set wachtWoord = ?, salt = ?, " 
		+ "hashP = ?, " + "where accounten.naam = ?;";
		try (PreparedStatement statement = connection.prepareStatement(str)) {
			statement.setString(1, account.getWachtWoord());
			statement.setString(2, account.getSalt());
			statement.setString(3, account.getHashP());
			statement.setString(4, account.getNaam());
			statement.executeUpdate();
			System.out.println(" Het bijweken van het nieuwe wachtwoord is geslagd ");
		} catch (Exception ex) {
			throw new updateException("Het aanpassen van het account is gezakt");
		}
	}

	/* DE KLASSE VAN WISSEN */
	public void DeletAccount(Account account) {

		String str = " delete from accounten " + "where accounten.naam = ?;";
		try (PreparedStatement statement = connection.prepareStatement(str)) {

			statement.setString(1, account.getNaam());
			statement.executeUpdate();
			System.out.println(" Het wissen van het record is geslagd ");
		} catch (Exception ex) {
			throw new updateException("Het wissen van het account is gezakt");
		}
	}

	/* DE KLASSE VAN DE INFORMATIE AANBODEN */
	public String ShowAccount(Account account) {
		String show;
		String str = "select Accounten.id, Accounten.naam," + " Accounten.wachtWoord ,Account_Type.type "
				+ "from Accounten, Account_Type where " + "Accounten.naam = ? and "
				+ "Accounten.accountStatus = Account_Type.id;";
		try (PreparedStatement statement = connection.prepareStatement(str)) {

			statement.setString(1, account.getNaam());
			ResultSet res = statement.executeQuery();
			if (res.next()) {

				int id = res.getInt(1);
				String naam = res.getString(2);
				String wachtwoord = res.getString(3);
				String type = res.getString(4);
				System.out.println(" &&&& Record informatie &&&& ");
				System.out.println("Id\t Naam\tWachtWoord\tType ");
				System.out.println("------------------------------------------------------");
				show = id + "\t" + naam + "\t" + wachtwoord + "\t\t" + type;
			}

			else
				show = "De naam is afwijzig";
		}

		catch (Exception ex) {
			throw new updateException(" Er is een probleem in de database ");
		}

		return show;
	}
	
	public boolean gebruikerNaamAanwezig(String naam) {
		boolean isWezig = false;
		String str = " select * from accounten where accounten.naam = ?;";
		try (PreparedStatement statement = connection.prepareStatement(str)) {
			statement.setString(1, naam);
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
	
	public boolean hashAanwezig(String hashP) {
		boolean isWezig = false;
		String str = " select * from accounten where accounten.hashP = ?;";
		try (PreparedStatement statement = connection.prepareStatement(str)) {
			statement.setString(1, hashP);
			ResultSet res = statement.executeQuery();
			if (res.next())
				isWezig = true;
			else
				isWezig = false;
		} catch (Exception ex) {
			throw new updateException(" Er is een probleem met database ");
		}
		return isWezig;	
}
	public String getSalt(String naam){
		String salt = null;
		String str = " select salt from accounten where accounten.naam = ?;";
		try (PreparedStatement statement = connection.prepareStatement(str)) {
			statement.setString(1, naam);
			ResultSet res = statement.executeQuery();
			if (res.next())
				salt = res.getString(1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return salt;
	}
	
	
}