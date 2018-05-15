package model;

import java.util.List;

public class Country extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1591563341844813190L;
	private Long id;
	private String name;
	private String description;
	private List<Tour> tours;

	public Country() {

	}

	public Country(Long id) {
		this.id = id;
	}
	public Country(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
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

	public List<Tour> getTours() {
		return tours;
	}

	public void setTours(List<Tour> tours) {
		this.tours = tours;
	}

}
