package application;

import java.sql.Date;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DetailedOrderInfo {
	private StringProperty customerFirstName, customerLastName, payment_method;
	private IntegerProperty tableNumber, orderNumber;
	private DoubleProperty tipAmount, orderTotal;
	private Date orderDate;
	
	public DetailedOrderInfo(){
		
	}
	
	public DetailedOrderInfo(String customerFirstName, String customerLastName,
			String payment_method, int tableNumber, int orderNumber,
			double tipAmount, double orderTotal, Date orderDate) {
		super();
		this.customerFirstName = new SimpleStringProperty(customerFirstName);
		this.customerLastName = new SimpleStringProperty(customerLastName);
		this.payment_method = new SimpleStringProperty(payment_method);
		this.tableNumber = new SimpleIntegerProperty(tableNumber);
		this.orderNumber = new SimpleIntegerProperty(orderNumber);
		this.tipAmount = new SimpleDoubleProperty(tipAmount);
		this.orderTotal = new SimpleDoubleProperty(orderTotal);
		this.orderDate = orderDate;
	}

	public String getCustomerFirstName() {
		return customerFirstName.get();
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = new SimpleStringProperty(customerFirstName);
	}

	public String getCustomerLastName() {
		return customerLastName.get();
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = new SimpleStringProperty(customerLastName);
	}

	public String getPayment_method() {
		return payment_method.get();
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = new SimpleStringProperty(payment_method);
	}

	public int getTableNumber() {
		return tableNumber.get();
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = new SimpleIntegerProperty(tableNumber);
	}

	public int getOrderNumber() {
		return orderNumber.get();
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = new SimpleIntegerProperty(orderNumber);
	}

	public double getTipAmount() {
		return tipAmount.get();
	}

	public void setTipAmount(double tipAmount) {
		this.tipAmount = new SimpleDoubleProperty(tipAmount);
	}

	public double getOrderTotal() {
		return orderTotal.get();
	}

	public void setOrderTotal(double orderTotal) {
		this.orderTotal = new SimpleDoubleProperty(orderTotal);
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date date) {
		this.orderDate = date;
	}
	
	
}
