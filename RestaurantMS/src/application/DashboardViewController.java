package application;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardViewController implements Initializable {
	@FXML private Button createNewOrderButton;
	@FXML private Button todayStatisticsButton;
	@FXML private Label dateLabel;
	
	
	public void createNewOrderButtonPushed(ActionEvent event) throws IOException{
		
		Parent tableViewParent = FXMLLoader.load(getClass().getResource("CreateOrderView.fxml"));
		Scene tableViewScene = new Scene(tableViewParent);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(tableViewScene);
		window.show();

	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    dateLabel.setText(strDate);
		
	}
	
	
}
