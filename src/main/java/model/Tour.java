package model;

import java.sql.Date;
import java.text.DateFormat;
import java.util.List;

public class Tour extends Model {

	
	private static final long serialVersionUID = -6731203010781904992L;
	
	private Long id;
	private String name;
	private String description;
	private int price;
	private Date date;
	private List<Country> countries;

	public Tour() {
		
	}
	
	public Tour(Long id) {
		super(id);
	}
	
	public Tour(Long id, String name, String description, int price, Date date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.date = date;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

}
