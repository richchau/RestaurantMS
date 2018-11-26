package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class NavigationViewController implements Initializable {

	@FXML private BorderPane mainBorderPane;
	@FXML private VBox sideVBox;
	@FXML private VBox navigationVBox;
	
	@FXML private ToggleGroup navigationToggleGroup;
	@FXML private ToggleButton dashboardToggleButton;
	@FXML private ToggleButton ordersToggleButton;
	@FXML private ToggleButton analyticsToggleButton;
	@FXML private ToggleButton staffToggleButton;
	@FXML private ToggleButton menuToggleButton;
	@FXML private ToggleButton tablesToggleButton;
	
    @FXML
    private void handleShowView(ActionEvent e) {
        String view = (String) ((Node)e.getSource()).getUserData();
        loadFXML(getClass().getResource(view));
    }

    private void loadFXML(URL url) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            mainBorderPane.setCenter(loader.load());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Sets the default center pane to the Dashboard
		loadFXML(getClass().getResource("DashboardView.fxml"));
		
		navigationToggleGroup = new ToggleGroup();
		dashboardToggleButton.setToggleGroup(navigationToggleGroup);
		ordersToggleButton.setToggleGroup(navigationToggleGroup);
		analyticsToggleButton.setToggleGroup(navigationToggleGroup);
		staffToggleButton.setToggleGroup(navigationToggleGroup);
		tablesToggleButton.setToggleGroup(navigationToggleGroup);
		menuToggleButton.setToggleGroup(navigationToggleGroup);
		navigationToggleGroup.selectToggle(dashboardToggleButton);
		
		
		navigationToggleGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
		    if (newVal == null)
		        oldVal.setSelected(true);
		});
		
		
	}
	
}
