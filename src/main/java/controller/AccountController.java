package controller;

//////////
import view.*;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import dao.AccountDAO;
import dbmanager.ConnectionManager;
import model.Account;
import security.*;

//////////
public class AccountController {
	private AccountDAO accountdao;
	private Connection connection;
	private ConnectionManager mn; 
	private Account account;
	private Validator validator = new Validator();
	private Scanner input = new Scanner(System.in);
	private HashWachtWoord hw = new HashWachtWoord();
	private SaltRandom saltRandom = new SaltRandom();
	boolean goedKeus = false;

	public AccountController() {
		mn = new ConnectionManager();
		accountdao = new AccountDAO(mn.getConnection());
	}

	/* Controller CLASS */
	public void InsertAccount() throws NoSuchAlgorithmException{

		account = new Account();
		System.out.println(" ***** Vul het account informatie in *****");
		System.out.print(" Vul naam in :");
		account.setNaam(input.nextLine());
		System.out.print(" Vul wachtWoord in :");
		String wachtWoord = input.nextLine();
		account.setWachtWoord(wachtWoord);
		String salt = saltRandom.SaltRandom();
		account.setSalt(salt);
		account.setHashP(hw.hashWachtWoord(wachtWoord, salt));

		do {
			System.out.print(" Vul de record status in :");
			String keus = input.nextLine();

			if (validator.inputStatus(keus)) {
				account.setAccountStatus(Integer.parseInt(keus));
				accountdao.InsertAccount(account);
				goedKeus = true;
			}
		} while (!(goedKeus));
	}

	public void UpdateAccountStatus() {
		account = new Account();
		System.out.println(" ***** Informatie Bijwerken *****");
		System.out.print(" Vul de naam in :");
		account.setNaam(input.nextLine());
		System.out.print("Kies  het nieuw accountStatus als 1: Admin" + " 2: Medewerker 3: Klant:");
		do {
			System.out.print(" Vul de record status in :");
			String keus = input.nextLine();

			if (validator.inputStatus(keus)) {
				account.setAccountStatus(Integer.parseInt(keus));
				accountdao.InsertAccount(account);
				goedKeus = true;
			}
		} while (!(goedKeus));
	}
	
	public void UpdateAccountWachtWoord() throws NoSuchAlgorithmException{
		account = new Account();
		System.out.println(" ***** Informatie Bijwerken *****");
		System.out.print(" Vul de naam in :");
		account.setNaam(input.nextLine());
		
		do {
			System.out.print("Vul u alstublieft het nieuw wachtwoord in :");
			String wachtwoord = input.nextLine();

			if (validator.correcteString(wachtwoord)) {
				account.setWachtWoord(wachtwoord);
				String salt = saltRandom.SaltRandom();
				account.setSalt(salt);
				account.setHashP(hw.hashWachtWoord(wachtwoord, salt));
				accountdao.InsertAccount(account);
				goedKeus = true;
			}
		} while (!(goedKeus));
	}

	public void DeleteAccount() {
		account = new Account();
		System.out.println(" ***** Informatie Wissen *****");
		System.out.print(" Vul de naam van de gebruiker in :");
		account.setNaam(input.nextLine());
		accountdao.DeletAccount(account);

	}

	public void ShowAccount() {
		account = new Account();
		System.out.print(" Vul de naam van de gebruiker in :");
		account.setNaam(input.nextLine());
		System.out.println(accountdao.ShowAccount(account));
	}
}
