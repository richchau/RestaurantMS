package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreateOrderViewController implements Initializable{
	
	//Items for MYSQL connection
	private DBClass objDbClass;
	private Connection connection;
	private ObservableList<MenuItem> menuItems;
	
	//Items to configure the table view of menu items
	@FXML private TableView<MenuItem> menuItemTableView;
	@FXML private TableColumn<MenuItem, String> menuItemNameColumn;
	@FXML private TableColumn<MenuItem, Double> priceColumn;
	@FXML private TableColumn<MenuItem, Button> actionColumn;
	
	//Items to configure the toggle buttons
	@FXML private ToggleButton appetizersToggleButton;
	@FXML private ToggleButton mainEntreesToggleButton;
	@FXML private ToggleButton dessertsToggleButton;
	@FXML private ToggleButton drinksToggleButton;
	private ToggleGroup itemTypeToggleGroup;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Initialize columns in item menu table view
		menuItemNameColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("itemName"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, Double>("price"));
		
		actionColumn.setCellFactory(ActionButtonTableCell.<MenuItem>forTableColumn("Add", (MenuItem p) -> {
		    return p;
		}));
				
		//Initialize toggle button group
		itemTypeToggleGroup = new ToggleGroup();
		appetizersToggleButton.setToggleGroup(itemTypeToggleGroup);
		mainEntreesToggleButton.setToggleGroup(itemTypeToggleGroup);
		dessertsToggleButton.setToggleGroup(itemTypeToggleGroup);
		drinksToggleButton.setToggleGroup(itemTypeToggleGroup);
		//Sets default toggle selection to appetizer
		appetizersToggleButton.setSelected(true);
		//Disables deselection of toggle buttons
		itemTypeToggleGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
		    if (newVal == null)
		        oldVal.setSelected(true);
		});
		
		//Initialize database connection
		objDbClass = new DBClass();
		
		try {
			connection = objDbClass.getConnection();
			switchItemMenuView("Appetizer");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * This method handles toggle button selections
	 */
	public void toggleButtonChanged(){
		if (itemTypeToggleGroup.getSelectedToggle().equals(appetizersToggleButton)){
			switchItemMenuView("Appetizer");
		}
		if (itemTypeToggleGroup.getSelectedToggle().equals(mainEntreesToggleButton)){
			switchItemMenuView("Main Entree");
		}
		if (itemTypeToggleGroup.getSelectedToggle().equals(dessertsToggleButton)){
			switchItemMenuView("Dessert");
		}
		if (itemTypeToggleGroup.getSelectedToggle().equals(drinksToggleButton)){
			switchItemMenuView("Drink");
		}
	}
	
	
	/**
	 * Changes item menu table view to chosen toggle
	 */
	public void switchItemMenuView(String type){
		menuItems = FXCollections.observableArrayList();
		try {
			String sql = "SELECT * FROM Menu_Item WHERE Item_Type = '"+ type +"'";
			ResultSet resultSet = connection.createStatement().executeQuery(sql);
			while(resultSet.next()){
				MenuItem item = new MenuItem();
				item.setMenuItemNumber(resultSet.getInt("Menu_Item_Number"));
				item.setItemName(resultSet.getString("Item_Name"));
				item.setItemType(resultSet.getString("Item_Type"));
				item.setPrice(resultSet.getDouble("Price"));
				item.setIsGlutenFree(resultSet.getBoolean("Gluten_Free"));
				item.setContainNuts(resultSet.getBoolean("Contain_Nuts"));
				item.setIngredients(resultSet.getString("Ingredients"));
				menuItems.add(item);
			}
			menuItemTableView.setItems(menuItems);
			
	
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
