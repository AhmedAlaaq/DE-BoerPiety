package login;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;

import dao.*;
import javafx.stage.Stage;
import model.Token;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.HPos;
import javafx.scene.shape.Line;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import dbmanager.ConnectionManager;
import security.*;
import view.Menu;
import controller.TokenController;
import javax.swing.JOptionPane;

public class LogIn extends Application {
	private ConnectionManager mn;
	private Connection connection;
	private AccountDAO accountdao;
	private static TokenController tokenController = new TokenController() ;
	private HashWachtWoord hw;
	private TextField gebruikerNaam = new TextField();
	private PasswordField wachtWoord = new PasswordField();
	private Button btLoggIn = new Button("LogIn");
	private Label userStatus = new Label("       ");
	private Stage primarystage;

	@Override
	public void start(Stage primarystage) throws Exception {

		GridPane gridPane = new GridPane();
		Text text = new Text(20, 20, "       LogIn scherm BoerPiets Applikaasie");
		gridPane.setStyle("-fx-background-color: lightblue");
		text.setFont(Font.font("SansSerif", FontWeight.BOLD, FontPosture.ITALIC, 15));
		gridPane.add(text, 1, 0);
		gridPane.add(new Label(""), 0, 1);
		gridPane.add(new Label("GebruikerNaam :"), 0, 2);
		gridPane.add(new Label("WachtWoord :"), 0, 3);
		gridPane.add(gebruikerNaam, 1, 2);
		gridPane.add(wachtWoord, 1, 3);
		gridPane.add(userStatus, 1, 4);
		gridPane.add(btLoggIn, 2, 5);
		gridPane.setHalignment(btLoggIn, HPos.RIGHT);
		btLoggIn.setOnAction(e -> {
			try {

				if (dbController()) {
					primarystage.close();
					tokenController.insert();
					Menu m = new Menu();
					m.runMenu();

				} else {

					JOptionPane.showMessageDialog(null, "Verkeerd Gebruikernaam of Wachtwoord");
					if (0 == JOptionPane.OK_OPTION) {
						gebruikerNaam.setText("");
						wachtWoord.setText("");

					}

				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		BorderPane pane = new BorderPane();
		pane.setCenter(gridPane);
		Scene scene = new Scene(pane, 500, 150);
		primarystage.setTitle("BoerPiets Applikaasie");
		primarystage.setScene(scene);
		primarystage.show();
	}

	private boolean dbController() throws Exception {
		mn = new ConnectionManager();
		String gebruikernaam = gebruikerNaam.getText().trim();
		String wachtwoord = new String(wachtWoord.getText());
		boolean isconnected = false;

		accountdao = new AccountDAO(mn.getConnection());
		if (accountdao.gebruikerNaamAanwezig(gebruikernaam)) {
			hw = new HashWachtWoord();
			String hash = HashWachtWoord.hashWachtWoord(wachtwoord, accountdao.getSalt(gebruikernaam));

			if (accountdao.hashAanwezig(hash))
				isconnected = true;
		}
		return isconnected;

	}
	public static int checkToken(){
		Token token = new Token();
		int isWezig = 0;

		if (!tokenController.iswezig())
			isWezig = 1;
		else {
			token = tokenController.getToken();
			LocalDateTime now = LocalDateTime.now();
			if (now.compareTo(token.getExpDate()) > 0){
				isWezig = 2;
			}
			else 
				isWezig = 3;
			}
			return isWezig;	
		
	}

	public static void main(String[] args) throws Exception {
		

		if (checkToken() == 1)
			Application.launch(args);
		
		
		else if (checkToken() == 2){
			tokenController.delete();
			Application.launch(args);
		}
		else if (checkToken() == 3){
				Menu menu = new Menu();
				menu.runMenu();
			}
	}

}
