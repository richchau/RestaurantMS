package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class OrderDetailPopUpViewController implements Initializable{
	
	private DBClass objDbClass;
	private Connection connection;
	private int orderNumber;
	private ObservableList<OrderLine> orderLineItems;
	
	//Item to configure button
	@FXML private Button closeButton;
	
	//Item to configure labels
	@FXML private Label orderNumberLabel;
	@FXML private Label dateLabel;
	@FXML private Label handlerLabel;
	@FXML private Label tableNumberLabel;
	@FXML private Label customerLabel;
	@FXML private Label paymentTypeLabel;
	@FXML private Label tipLabel;
	@FXML private Label totalLabel;
	
	//Items to configure table 
	@FXML private TableView<OrderLine> orderLineTableView;
	@FXML private TableColumn<OrderLine, String> itemTableColumn;
	@FXML private TableColumn<OrderLine, Integer> quantityTableColumn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Initializes table view
		itemTableColumn.setCellValueFactory(new PropertyValueFactory<OrderLine, String>("menuItem"));
		quantityTableColumn.setCellValueFactory(new PropertyValueFactory<OrderLine, Integer>("quantity"));
		
		//Initialize database connection
		objDbClass = new DBClass();

		try {
			connection = objDbClass.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void initData(DetailedOrderInfo order){
		orderNumber = order.getOrderNumber();
		
		orderNumberLabel.setText("Order #" + orderNumber);
		dateLabel.setText(order.getOrderDate().toString());
		//handlerLabel.setText(order.getHandler());
		tableNumberLabel.setText(Integer.toString(order.getTableNumber()));
		customerLabel.setText(order.getCustomerFirstName() + " " + order.getCustomerLastName());
		paymentTypeLabel.setText(order.getPayment_method());
		tipLabel.setText(Double.toString(order.getTipAmount()));
		totalLabel.setText(Double.toString(order.getOrderTotal()));
		
		getDataForTable();
	}
	
	public void getDataForTable(){
		orderLineItems = FXCollections.observableArrayList();
		String sql = "SELECT * FROM orderline JOIN menu_item ON orderline.menu_item = menu_item.menu_item_number WHERE order_info =" + orderNumber;
		try {
			ResultSet resultSet = connection.createStatement().executeQuery(sql);
			while(resultSet.next()){
				OrderLine orderLineItem = new OrderLine();
				orderLineItem.setMenuItem(resultSet.getString("Item_Name"));
				orderLineItem.setQuantity(resultSet.getInt("quantity"));
				
				orderLineItems.add(orderLineItem);
			}
			
			orderLineTableView.setItems(orderLineItems);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void closeStage(){
		Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();
	}

}
