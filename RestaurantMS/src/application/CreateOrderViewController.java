package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;
import java.text.DecimalFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreateOrderViewController implements Initializable{
	
	//Items for MYSQL connection
	private DBClass objDbClass;
	private Connection connection;
	private ObservableList<MenuItem> menuItems;
	private ObservableList<OrderLine> orderLineItems;
	
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
	
	//Items to configure the table view of order line
	@FXML private TableView<OrderLine> orderLineTableView;
	@FXML private TableColumn<OrderLine, String> menuItemOrderLineNameColumn;
	@FXML private TableColumn<OrderLine, Integer> quantityColumn;
	@FXML private TableColumn<OrderLine, Double> computedPriceColumn;
	@FXML private TableColumn<OrderLine, Button> orderLineActionColumn;
	
	//Items to configure the order details 
	@FXML private TextField tipAmountTextField;
	@FXML private ChoiceBox<String> paymentTypeChoiceBox;
	@FXML private Label taxLabel;
	@FXML private Label balanceLabel;
	//temp
	@FXML private TextField orderNumberTextField;
	
	//Configures create order button
	@FXML private Button createOrderButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Initialize columns in item menu table view
		menuItemNameColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("itemName"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, Double>("price"));
		
		actionColumn.setCellFactory(ActionButtonTableCell.<MenuItem>forTableColumn("Add", (MenuItem p) -> {
			addToOrderLine(p);
		    updateOrderLineTableView();
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
		
		
		//Initialize columns in order line table view
		menuItemOrderLineNameColumn.setCellValueFactory(new PropertyValueFactory<OrderLine, String>("menuItem"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<OrderLine, Integer>("quantity"));
		//quantityColumn.setCellFactory(ActionChoiceBoxTableCell.<OrderLine>forTableColumn());
		computedPriceColumn.setCellValueFactory(new PropertyValueFactory<OrderLine, Double>("computedPrice"));
		
		orderLineItems = FXCollections.observableArrayList();
		orderLineItems.addListener((ListChangeListener<OrderLine>) c ->{
			System.out.println("Order Line Modified");
			computeTotalBalance();
		});
		
		orderLineActionColumn.setCellFactory(ActionButtonTableCell.<OrderLine>forTableColumn("X", (OrderLine p) -> {
			orderLineItems.remove(p);
			computeTotalBalance();
			updateOrderLineTableView();
			return p;
		}));
		
		
		//Initializes order details
		paymentTypeChoiceBox.getItems().addAll("card", "cash");
		paymentTypeChoiceBox.getSelectionModel().selectFirst();
		
		tipAmountTextField.textProperty().addListener(new ChangeListener<String>() {
			
			@Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            tipAmountTextField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		    
		});
		
		tipAmountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		    computeTotalBalance();
		});
		
		taxLabel.setText("0.00");
		balanceLabel.setText("0.00");
		
		
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
	
	/**
	 * This method adds to the order line. Creates a new entry for new items, increments quantity for items
	 * that already exists
	 */
	public void addToOrderLine(MenuItem item){
		for(OrderLine orderLine: orderLineItems){
			if (item.getItemName().equals(orderLine.getMenuItem())){
				orderLine.addQuantity();
					computeTotalBalance();
				return;
			}
		}
		orderLineItems.add(new OrderLine(item));
		computeTotalBalance();
	}
	
	/**
	 * Updates the order line table view
	 */
	public void updateOrderLineTableView(){
		orderLineTableView.getItems().clear();
		orderLineTableView.getItems().addAll(orderLineItems);
	}
	
	/**
	 * This method computes the total balance of the order and sets the
	 * tax and balance labels
	 */
	public void computeTotalBalance(){	
		DecimalFormat dFormat = new DecimalFormat("#.00");
		
		double orderTotal = 0;
		double tax = 0;
		double balance = 0;
		
		for(OrderLine orderLine: orderLineItems){
			orderTotal += orderLine.getComputedPrice();
		}
		
		tax = orderTotal * .0725;
		
		if (!tipAmountTextField.getText().isEmpty()){
			balance += Double.parseDouble(tipAmountTextField.getText());
		}
		
		balance += orderTotal + tax;
		
		taxLabel.setText((dFormat.format(tax)));
		balanceLabel.setText((dFormat.format(balance)));
	}
	
	
	/**
	 * This method creates a new order 
	 */
	public void createNewOrder(){
		
		
		
	}
	
}
