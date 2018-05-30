package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.List;

import model.User;



public interface UserDao extends AbstractDao<User> {
	public User getByLogin(String login)  throws SQLException ;
	public User getUser(String login, String password);
	
	public User getUserByEmail(String email)  throws SQLException ;

	public List<User> getUserByName(String name, String lastName) throws SQLException ;

	public List<User> getUserByDob(Date dob) throws SQLException ;

	public List<User> getUserByAgeRange(int ageFrom, int ageTo) throws SQLException ;
	

}
