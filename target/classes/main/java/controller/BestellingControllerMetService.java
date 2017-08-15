package controller;

import java.sql.Connection;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dao.BestellingService;
import model.*;
import view.Menu;
import view.Validator;

public class BestellingControllerMetService {

	private BestellingService bestellingService;
	private Validator validator = new Validator();
	private Artikel artikel;
	private Klant klant;
	private Connection connection;
	private boolean goedKeus = false;
	private Scanner input = new Scanner(System.in);
	final Logger logger = LoggerFactory.getLogger(Menu.class);

	public BestellingControllerMetService() {
		bestellingService = new BestellingService();
	}

	public void insert() {
		Bestelling bestelling = new Bestelling();
		BestelRegel regel = new BestelRegel();

		do {
			System.out.print("Vul de achternaam van de klant in :");
			String achternaam = input.nextLine();
			if (bestellingService.bestellingdao().wezig(achternaam)) {
				bestelling.setKlant(bestellingService.bestellingdao().getKlant(achternaam));
				goedKeus = true;
			}
		} while (!(goedKeus));

		bestelling = bestellingService.bestellingdao().insertBestelling(bestelling);
		regel.setBestel(bestelling);

		System.out.print("Hoeveel artiklen wilt u :");
		int num = input.nextInt();
		for (int i = 0; i < num; i++) {
			System.out.print(" Vul het antaal van kazen in :");
			regel.setAntaal(input.nextInt());
			do {
				System.out.print("Vul de ID van het artikel in :");
				
				String artikelId = input.nextLine();
				if (validator.correcteInteger(artikelId) && bestellingService.artikeldao().wezig(Integer.parseInt(artikelId))) {
					regel.setArtikel(bestellingService.bestelregeldao().getArtikel(Integer.parseInt(artikelId)));
					regel = bestellingService.bestelregeldao().insertBestelRegel(regel);
					goedKeus = true;
				}

			} while (!(goedKeus));
			bestellingService.bestellingdao().addBestelregel(bestelling);
			System.out.println(bestelling);
		}
	}

	public void delete() {
		Bestelling bestelling = new Bestelling();
		do {
			System.out.print("Vul u het id van de Bestelling in :");
			String id = input.nextLine();
			if (validator.correcteInteger(id)) {
				if (!bestellingService.bestellingdao().wezig(Integer.parseInt(id)))
					System.out.println(" Deze bestelling is afwijzig ! ");
				else

					bestellingService.bestellingdao().deleteBestelling(Integer.parseInt(id));
				goedKeus = true;
			}
		} while (!(goedKeus));

	}

	public void update(int id, int keus) {
		String bestelregelId = null;

		BestelRegel regel = new BestelRegel();
		Bestelling bestelling = new Bestelling();

		if (!bestellingService.bestellingdao().wezig(id))
			System.out.println(" De bestelling is afwijzig ! ");
		else {
			bestelling = bestellingService.bestellingdao().getBestelling(id);
			boolean isJuist;
			switch (keus) {
			case 1:
				do {
					System.out.print("Vul de ID van het artikel in :");
					String artikelId = input.nextLine();
					if (validator.correcteInteger(artikelId) && bestellingService.artikeldao().wezig(Integer.parseInt(artikelId))) {
						artikel = bestellingService.bestelregeldao().getArtikel(Integer.parseInt(artikelId));
						regel.setArtikel(artikel);
						goedKeus = true;
					}
				} while (!(goedKeus));
				System.out.print("Vul het antaal van kazen voor het nieuwe bestelregel in :");
				regel.setAntaal(input.nextInt());
				regel.setBestel(bestellingService.bestellingdao().getBestelling(id));
				regel = bestellingService.bestelregeldao().insertBestelRegel(regel);
				bestelling.setTotaalPrijs(bestelling.Totaalprijs(bestellingService.bestelregeldao().getRegelen(id)));
				bestellingService.bestellingdao().addBestelregel(bestelling);
				break;
			case 2:
				do {
					printRegelenVanBestelling(id);
					System.out.print("Vul de ID van het Bestel regel in :");
					bestelregelId = input.nextLine();
					if (validator.correcteInteger(bestelregelId) && bestellingService.bestelregeldao().wezig(Integer.parseInt(bestelregelId))) {
						bestellingService.bestellingdao().deleteBestelregel(bestellingService.bestelregeldao().getBestelRegel(Integer.parseInt(bestelregelId)), bestellingService.bestellingdao().getBestelling(id));
						goedKeus = true;
					}
				} while (!(goedKeus));

				break;
			case 3:

				do {
					System.out.print("Vul de ID van het Bestel regel in :");
					bestelregelId = input.nextLine();
					if (validator.correcteInteger(bestelregelId) && bestellingService.bestelregeldao().wezig(Integer.parseInt(bestelregelId))) {
						System.out.print("Vul het nieuw aantal van de kazen in :");
						String aantal = input.nextLine();
						if (validator.correcteInteger(aantal))
							bestellingService.bestellingdao().updateBestelling(id, bestellingService.bestelregeldao().updateBestelregel(Integer.parseInt(bestelregelId), Integer.parseInt(aantal)));
						goedKeus = true;
					}
				} while (!(goedKeus));

				break;

			}
		}
	}

	public void printRegelenVanBestelling(int bestelId) {
		Bestelling bestelling = bestellingService.bestellingdao().getBestelling(bestelId);
		Set<BestelRegel> regelen = bestellingService.bestelregeldao().getRegelen(bestelling.getId());
		System.out.println(" Kies u Alstublieft welke bestel regel wilt u wissen ");
		for (BestelRegel regel : regelen)
			printBestelRegel(regel);
	}

	public void printBestelRegel(BestelRegel regel) {
		System.out.println("\t" + regel.getId() + "\t " + regel.getArtikel().getNaam() + "\t\t"
				+ regel.getArtikel().getPrijs() + "\t\t" + regel.getAntaal());
	}

	public void printBestellingen() {
		Set<Bestelling> bestellingen = bestellingService.bestellingdao().getBestellingen();
		System.out.println(" &&&& Bestellingen informatie &&&& ");
		System.out.println("------------------------------------");
		for (Bestelling bestelling : bestellingen) {
			Set<BestelRegel> regelen = bestellingService.bestelregeldao().getRegelen(bestelling.getId());
			System.out.println("Bestelling " + bestelling.getId() + " details :");
			System.out.println("Bestelling ID\t   KlantAchternaam\t    TotaalPrijs ");
			System.out.println(" --------------------------------------------------------");
			System.out.println("\t" + bestelling.getId() + "\t\t" + bestelling.getKlant().getAchternaam() + "\t\t"
					+ bestelling.getTotaalPrijs());
			System.out.println(" &&&& Bestelregel informatie &&&& ");
			System.out.println("----------------------------------");
			System.out.println(" Bestel ID\t ArtikelNaam\t\tArtikelPrijs\tAntaal");
			System.out.println(" -------------------------------------------------------------");
			for (BestelRegel regel : regelen)
				printBestelRegel(regel);

		}
	}
	
	/*public void deleteBestellingenbyAchterNaam(String achterNaam){
		Set<Bestelling> bestellingen = bestellingService.bestellingdao().getBestellingen();
		
		for (Bestelling bestelling: bestellingen){
			if (bestelling.getKlant().getAchternaam() == achterNaam)
				bestellingService.bestellingdao().deleteBestelling(bestelling.getId());
		}

	}*/
}
