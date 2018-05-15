package model;

public class Role extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9156722239724478628L;
	private String name;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Role() {

	}

	public Role(Long id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
