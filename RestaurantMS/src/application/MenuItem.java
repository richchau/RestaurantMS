package application;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MenuItem {
	private IntegerProperty menuItemNumber;
	private StringProperty itemName, itemType, ingredients;
	private DoubleProperty price;
	private BooleanProperty containNuts, isGlutenFree;
	
	public MenuItem(){
		
	}
	
	public MenuItem(int menuItemNumber, String itemName, String itemType, 
			double price, boolean isGlutenFree, boolean containNuts, String ingredients) {
		
		this.menuItemNumber = new SimpleIntegerProperty(menuItemNumber);
		this.itemName = new SimpleStringProperty(itemName);
		this.itemType = new SimpleStringProperty(itemType);
		this.ingredients = new SimpleStringProperty(ingredients);
		this.price = new SimpleDoubleProperty(price);
		this.containNuts = new SimpleBooleanProperty(containNuts);
		this.isGlutenFree = new SimpleBooleanProperty(isGlutenFree);
	}

	public int getMenuItemNumber() {
		return menuItemNumber.get();
	}

	public void setMenuItemNumber(int menuItemNumber) {
		this.menuItemNumber = new SimpleIntegerProperty(menuItemNumber);
	}

	public String getItemName() {
		return itemName.get();
	}

	public void setItemName(String itemName) {
		this.itemName = new SimpleStringProperty(itemName);
	}

	public String getItemType() {
		return itemType.get();
	}

	public void setItemType(String itemType) {
		this.itemType = new SimpleStringProperty(itemType);
	}

	public String getIngredients() {
		return ingredients.get();
	}

	public void setIngredients(String ingredients) {
		this.ingredients = new SimpleStringProperty(ingredients);
	}

	public Double getPrice() {
		return price.get();
	}

	public void setPrice(double price) {
		this.price = new SimpleDoubleProperty(price);
	}

	public boolean getContainNuts() {
		return containNuts.get();
	}

	public void setContainNuts(boolean containNuts) {
		this.containNuts = new SimpleBooleanProperty(containNuts);
	}

	public boolean getIsGlutenFree() {
		return isGlutenFree.get();
	}

	public void setIsGlutenFree(boolean isGlutenFree) {
		this.isGlutenFree = new SimpleBooleanProperty(isGlutenFree);
	}
	
	
	
}
