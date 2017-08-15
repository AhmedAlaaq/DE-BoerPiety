package dao;

import dbmanager.ConnectionManager;
import java.sql.Connection;


public  class BestellingService {
	private Connection connection;
	private ConnectionManager mn;
	private KlantDAOMysql klantdao;
	private ArtikelDAO artikeldao;
	private BestellingDAO bestellingdao;
	private BestelRegelDAO bestelregeldao;
	public BestellingService(){
		mn = new ConnectionManager();
	}
	public ArtikelDAO artikeldao(){
	return artikeldao = new ArtikelDAO(mn.getConnection());
	}
	public BestellingDAO bestellingdao(){
		return bestellingdao = new BestellingDAO (mn.getConnection());
	}
	public BestelRegelDAO bestelregeldao(){
		 return bestelregeldao = new BestelRegelDAO(mn.getConnection());
	}
		

}
