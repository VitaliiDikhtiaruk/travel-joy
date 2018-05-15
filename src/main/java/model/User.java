package model;

import java.sql.Date;
import java.text.DateFormat;
import java.util.List;

public class User extends Model {

	private static final long serialVersionUID = -7258755254682403527L;

	private Long id;
	private String name;
	private String lastName;
	private int age;
	private String email;
	private Date dob;
	private String login;
	private String password;
	private String role;
	
	public void setRole(String role) {
		this.role = role;
	}

	private List<Role> roles;
	
	public User(Long id, String name, String lastName, int age, String email, Date dob, String login, String password, String role) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.dob = dob;
		this.login = login;
		this.password = password;
		this.role = role;
		
	}
	public String getRole() {
		return role;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User(Long id) {
		super(id);
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
