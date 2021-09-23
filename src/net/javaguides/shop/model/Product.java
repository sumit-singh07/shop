package net.javaguides.shop.model;

public class Product {
	int product_id;
	String product_name;
	int product_price;
	String product_description;
	
	
	public Product(int product_id, String product_name, int product_price, String product_description) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_price = product_price;
		this.product_description = product_description;
	}

	public Product(String product_name, int product_price, String product_description) {
		super();
		this.product_name = product_name;
		this.product_price = product_price;
		this.product_description = product_description;
	}
	
	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getProduct_price() {
		return product_price;
	}

	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}

	public String getProduct_description() {
		return product_description;
	}

	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}

}
