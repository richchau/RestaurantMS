package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Parent root;
		try {
			//root = FXMLLoader.load(getClass().getResource("CreateOrderView.fxml"));
			root = FXMLLoader.load(getClass().getResource("NavigationView.fxml"));
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			//primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

