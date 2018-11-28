package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OrderDetailPopUpViewController implements Initializable{

	@FXML private Button closeButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
	}
	
	public void closeStage(){
		Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();
	}

}
