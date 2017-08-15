package dao;
import model.Klant;
import java.util.List;

public interface KlantInterface {
	void insertKlant (Klant klant );
	void deleteKlant (int id);
	boolean wezig(String achternaam);
	void updateKlantnaam(String achternaam, Klant klant);
	void updateKlantAdres(String achtrenaam, Klant klant);
	List<Klant> kiesKlant(String achternaam);
	List<Klant> getKlanten();
	String KlantString(Klant klant);

}
