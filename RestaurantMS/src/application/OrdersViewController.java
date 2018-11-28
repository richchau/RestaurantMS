package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OrdersViewController implements Initializable {

	private DBClass objDbClass;
	private Connection connection;
	private ObservableList<DetailedOrderInfo> orderHistoryItems;
	
	//Items to configure the order history table
	@FXML private TableView<DetailedOrderInfo> orderHistoryTableView;
	@FXML private TableColumn<DetailedOrderInfo, Integer> orderNumberColumn;
	@FXML private TableColumn<DetailedOrderInfo, Integer> tableNumberColumn;
	@FXML private TableColumn<DetailedOrderInfo, Date> dateColumn;
	@FXML private TableColumn<DetailedOrderInfo, String> handlerColumn;
	@FXML private TableColumn<DetailedOrderInfo, Double> totalPaidColumn;
	@FXML private TableColumn<DetailedOrderInfo, Button> actionColumn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderHistoryTableView.refresh();
		
		//Initialize columns in order history table view
		orderNumberColumn.setCellValueFactory(new PropertyValueFactory<DetailedOrderInfo, Integer>("orderNumber"));
		tableNumberColumn.setCellValueFactory(new PropertyValueFactory<DetailedOrderInfo, Integer>("tableNumber"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<DetailedOrderInfo, Date>("orderDate"));
		//handlerColumn.setCellValueFactory(new PropertyValueFactory<DetailedOrderInfo, String>("handler"));
		totalPaidColumn.setCellValueFactory(new PropertyValueFactory<DetailedOrderInfo, Double>("orderTotal"));
		actionColumn.setCellFactory(ActionButtonTableCell.<DetailedOrderInfo>forTableColumn("View", (DetailedOrderInfo p) -> {
			showOrderDetails(p);
			return p;
		}));
		
		
		//Initialize database connection
		objDbClass = new DBClass();
		
		try {
			connection = objDbClass.getConnection();
			getOrderHistoryData();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method gets data from DB and populates the order history table view 
	 */
	public void getOrderHistoryData(){
		orderHistoryItems = FXCollections.observableArrayList();
		
		String sql = "SELECT Customer.first_name, Customer.last_name, Seats.table_number, Order_Info.order_number, Order_Info.order_date, " 
					+"Order_Info.tip_amount, Order_Info.order_total, Order_Info.payment_method "
					+"FROM Cust_Orders "
					+"INNER JOIN Customer ON Cust_Orders.customer = Customer.customer_id "
					+"INNER JOIN Seats ON Cust_Orders.seats = Seats.table_number "
					+"INNER JOIN Order_Info ON Cust_Orders.order_info = Order_Info.order_number;";
		
		try {
			ResultSet resultSet = connection.createStatement().executeQuery(sql);
			while(resultSet.next()){
				DetailedOrderInfo orderDetails = new DetailedOrderInfo();
				
				orderDetails.setCustomerFirstName(resultSet.getString("First_Name"));
				orderDetails.setCustomerLastName(resultSet.getString("Last_Name"));
				orderDetails.setOrderDate(resultSet.getDate("order_date"));
				orderDetails.setOrderNumber(resultSet.getInt("order_number"));
				orderDetails.setOrderTotal(resultSet.getDouble("order_total"));
				orderDetails.setPayment_method(resultSet.getString("payment_method"));
				orderDetails.setTableNumber(resultSet.getInt("table_number"));
				orderDetails.setTipAmount(resultSet.getDouble("tip_amount"));
				
				orderHistoryItems.add(orderDetails);
			}
			
			orderHistoryTableView.setItems(orderHistoryItems);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method pulls up details for the selected order 
	 */
	public void showOrderDetails(DetailedOrderInfo info){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderDetailPopUpView.fxml"));
			Stage popUpStage = new Stage(StageStyle.TRANSPARENT);
			popUpStage.setScene(new Scene((Pane) loader.load(), Color.TRANSPARENT));
			popUpStage.initModality(Modality.APPLICATION_MODAL);
			
			OrderDetailPopUpViewController controller = loader.<OrderDetailPopUpViewController>getController();
			controller.initData(info);
			
			
			
			popUpStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Scene scene = new Scene(root);

		
	}
}
