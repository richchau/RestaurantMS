package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class OrderLine {
	private String menuItem;
	private SimpleIntegerProperty quantity;
	private SimpleDoubleProperty computedPrice;
	
	private int orderInfoNumber;
	private int menuItemNumber;
	
	public OrderLine(){
		
	}
	
	public OrderLine(MenuItem menuItem){
		this.menuItem = menuItem.getItemName();
		quantity = new SimpleIntegerProperty(1);
		computedPrice = new SimpleDoubleProperty();
		//Binds computed price with quantity
		computedPrice.bind(quantity.multiply(menuItem.getPrice()));
		
		
		menuItemNumber = menuItem.getMenuItemNumber();
	}

	public String getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(String menuItem) {
		this.menuItem = menuItem;
	}

	public int getQuantity() {
		return quantity.get();
	}

	public void setQuantity(int quantity) {
		this.quantity = new SimpleIntegerProperty(quantity);
	}
	
	public void addQuantity(){
		this.quantity.setValue(quantity.getValue() + 1);
	}

	public double getComputedPrice() {
		return computedPrice.get();
	}

	public void setComputedPrice(double computedPrice) {
		this.computedPrice = new SimpleDoubleProperty(computedPrice);
	}

	public int getOrderInfoNumber() {
		return orderInfoNumber;
	}

	public void setOrderInfoNumber(int orderInfoNumber) {
		this.orderInfoNumber = orderInfoNumber;
	}

	public int getMenuItemNumber() {
		return menuItemNumber;
	}

	public void setMenuItemNumber(int menuItemNumber) {
		this.menuItemNumber = menuItemNumber;
	}
	
	

}
