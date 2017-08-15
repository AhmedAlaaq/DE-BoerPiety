package view;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import controller.AccountController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountMenu {
	private Menu menu = new Menu();
	private AccountController accountcontroller;
	final Logger logger = LoggerFactory.getLogger(AccountMenu.class);
	Validator validator = new Validator();
	Scanner input = new Scanner(System.in);

	public AccountMenu() throws Exception {
		accountcontroller = new AccountController();
		System.out.println("\n &&& Account Menu &&&");
		System.out.println("1. Invulling ");
		System.out.println("2. Bijwerken");
		System.out.println("3. Wissen");
		System.out.println("4. Aanboden");
		System.out.println("5. Exit");
		System.out.print(" Vul u alstublieft uw keus in :");
		String keus = input.nextLine();
		if (validator.correcteKeus(keus))
			switch (keus) {
			case "1":
				accountcontroller.InsertAccount();
				new AccountMenu();
				break;
			case "2":
				accountUpdatingMenu();
				new AccountMenu();
				break;
			case "3":
				accountcontroller.DeleteAccount();
				new AccountMenu();
				break;
			case "4":
				accountcontroller.ShowAccount();
				new AccountMenu();
				break;
			case "5":
				logger.warn("\nWilt u alstubliefd naar hoofdmenu terug ?");
				int answer = validator.uitLoggen(input.nextLine());
				switch (answer) {
				case 1:
					menu.runMenu();
					;
					break;
				case 2:
					System.out.println();
					new AccountMenu();
					break;
				default:
					logger.info(" U moet een passende keus geven ! Probeer nog een keer ");
					System.out.println();
					new AccountMenu();

				}
				break;
			default:
				logger.warn(" U moet een passende nummer geven ! Probeer nog een keer  ");
				new AccountMenu();
			}
		else
			new AccountMenu();

	}

	public void accountUpdatingMenu() throws Exception {

		System.out.println("          Het Aanpassen Menu           ");
		System.out.println("---------------------------------------");
		System.out.println("1. Het aanpassen van account status  ");
		System.out.println("2. Het aanpassen van account wachtwoord ");
		System.out.println("3. Exit");

		System.out.print(" Wat wilt u aanpassen ? ");
		String keus = input.nextLine();
		if (validator.inputStatus(keus))
			switch (keus) {
			case "1":
				accountcontroller.UpdateAccountStatus();
				break;
			case "2":
				accountcontroller.UpdateAccountWachtWoord();
				break;
			case "3":
				new AccountMenu();
				break;
			}
		else
			accountUpdatingMenu();

	}

}
