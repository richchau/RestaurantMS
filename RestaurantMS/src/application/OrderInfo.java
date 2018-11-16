package application;

import java.time.LocalDate;

public class OrderInfo {
	private int orderNumber;
	private LocalDate date;
	private double tipAmount;
	private double orderTotal;
	private String paymentMethod;
	
	public OrderInfo(int orderNumber, LocalDate date, double tipAmount, double orderTotal, String paymentMethod) {
		this.orderNumber = orderNumber;
		this.date = date;
		this.tipAmount = tipAmount;
		this.orderTotal = orderTotal;
		this.paymentMethod = paymentMethod;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getTipAmount() {
		return tipAmount;
	}

	public void setTipAmount(double tipAmount) {
		this.tipAmount = tipAmount;
	}

	public double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
	
	
}
