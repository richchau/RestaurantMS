package application;

public class OrderLine {
	private String menuItem;
	private int quantity;
	private double computedPrice;
	
	public OrderLine(MenuItem menuItem){
		this.menuItem = menuItem.getItemName();
		quantity = 1;
		computedPrice = menuItem.getPrice() * quantity;
	}

	public String getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(String menuItem) {
		this.menuItem = menuItem;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void addQuantity(){
		this.quantity++;
	}

	public double getComputedPrice() {
		return computedPrice;
	}

	public void setComputedPrice(double computedPrice) {
		this.computedPrice = computedPrice;
	}

}
