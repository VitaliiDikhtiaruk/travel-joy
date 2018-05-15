package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import dao.UserDao;
import dao.impl.CountryDatabaseDao.CountryDatabaseMySQL;
import dao.impl.TourDatabaseDao.TourDatabaseMySQL;
import model.Tour;
import model.User;

public class UserDatabaseDao implements UserDao {

	private String jdbcURL = "jdbc:mysql://localhost:3306/travel_agency";
	private String jdbcUsername = "root";
	private String jdbcPassword = "root";
	private Connection connection;

	private static Logger log = Logger.getLogger(UserDatabaseDao.class.getName());

	public UserDatabaseDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}

	protected void connect() throws SQLException {
		if (connection == null || connection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				log.warning("can't access to the database");
				throw new SQLException(e);
			}
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}

	protected void disconnect() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

	/*
	 * return list of all Users
	 * 
	 * @see dao.AbstractDao#getAll()
	 */
	@Override
	public List<User> getAll() throws SQLException {
		log.info("getting list of all Users");
		List<User> users = new ArrayList<>();
		connect();
		try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users")) {
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("user_id");
				String name = rs.getString("user_name");
				String lastName = rs.getString("user_LastName");
				int age = rs.getInt("user_age");
				String email = rs.getString("user_email");
				Date dob = rs.getDate("user_dateOfBirth");
				String login = rs.getString("user_login");
				String password = rs.getString("user_password");
				String role = rs.getString("user_role");
				User user = new User(id, name, lastName, age, email, dob, login, password, role);
				users.add(user);
			}
			rs.close();
			statement.close();

		} catch (SQLException e) {
			log.warning("can't get list users from the table");
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
		return users;
	}

	/*
	 * return User by id
	 * 
	 * @see dao.AbstractDao#getById(java.lang.Long)
	 */
	@Override
	public User getById(Long id) throws SQLException {
		log.info("getting user by id " + id);
		User result = null;
		connect();
		try (PreparedStatement statement = connection.prepareStatement(UserDatabaseMySQL.GET.QUERY)) {
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				String name = rs.getString("user_name");
				String lastName = rs.getString("user_lastName");
				int age = rs.getInt("user_age");
				String email = rs.getString("user_email");
				Date dob = rs.getDate("user_dateOfBirth");
				String login = rs.getString("user_login");
				String password = rs.getString("user_password");
				String role = rs.getString("user_role");
				result = new User(id, name, lastName, age, email, dob, login, password, role);
				rs.close();
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warning("no such id " + id);
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
		return result;
	}

	/*
	 * adding User to Database
	 * 
	 * @see dao.AbstractDao#add(model.Model)
	 */
	@Override
	public void add(User user) throws SQLException {
		log.info("adding user " + user.getName());
		connect();

		try (PreparedStatement statement = connection.prepareStatement(UserDatabaseMySQL.ADD.QUERY)) {
			statement.setString(1, user.getName());
			statement.setString(2, user.getLastName());
			statement.setInt(3, user.getAge());
			statement.setString(4, user.getEmail());
			statement.setDate(5, user.getDob());
			statement.setString(6, user.getLogin());
			statement.setString(7, user.getPassword());
			statement.setString(8, user.getRole());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			log.warning("can't create new user" + user.getName());
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
	}

	/*
	 * update User in the Database
	 * 
	 * @see dao.AbstractDao#update(model.Model)
	 */

	@Override
	public void update(User user) throws SQLException {
		log.info("updating user " + user.getName());
		connect();

		try (PreparedStatement statement = connection.prepareStatement(UserDatabaseMySQL.UPDATE.QUERY)) {
			statement.setString(1, user.getName());
			statement.setString(2, user.getLastName());
			statement.setInt(3, user.getAge());
			statement.setString(4, user.getEmail());
			statement.setDate(5, user.getDob());
			statement.setString(6, user.getLogin());
			statement.setString(7, user.getPassword());
			statement.setString(8, user.getRole());
			statement.setLong(9, user.getId());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			log.warning("can't update user " + user.getName());
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
	}


	/*
	 * delete User from the Database
	 * 
	 * @see dao.AbstractDao#remove(model.Model)
	 */

	@Override
	public void remove(User user) throws SQLException {
		log.info("removing user " + user.getName());
		connect();
		try (PreparedStatement statement = connection.prepareStatement(UserDatabaseMySQL.REMOVE.QUERY)) {
			statement.setLong(1, user.getId());
			statement.close();
		} catch (SQLException e) {
			log.warning("can't update user " + user.getName());
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}

	}


	/*
	 * return User by login
	 * 
	 * @see dao.AbstractDao#getById(java.lang.Long)
	 */
	@Override
	public User getByLogin(String userLogin) throws SQLException {
		log.info("getting user by login " + userLogin);
		User result = null;
		connect();
		try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE user_login = (?)")) {
			statement.setString(1, userLogin);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				Long id = rs.getLong("user_id");
				String name = rs.getString("user_name");
				String lastName = rs.getString("user_lastName");
				int age = rs.getInt("user_age");
				String email = rs.getString("user_email");
				Date dob = rs.getDate("user_dateOfBirth");
				String login = rs.getString("user_login");
				String password = rs.getString("user_password");
				String role = rs.getString("user_role");
				result = new User(id, name, lastName, age, email, dob, login, password, role);
				rs.close();
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warning("no such id " + userLogin);
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
		return result;
	}


	/*
	 * return User by name and lastName
	 * 
	 * @see dao.AbstractDao#getById(java.lang.Long)
	 */
	@Override
	public List<User> getUserByName(String userName, String userLastName) throws SQLException {
		log.info("getting list users by name and lastName " + userName + " " + userLastName);
		List<User> users = new ArrayList<>();
		connect();
		try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE user_name = (?) AND user_lastName = (?)")) {
			statement.setString(1, userName);
			statement.setString(2, userLastName);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("user_id");
				String name = rs.getString("user_name");
				String lastName = rs.getString("user_LastName");
				int age = rs.getInt("user_age");
				String email = rs.getString("user_email");
				Date dob = rs.getDate("user_dateOfBirth");
				String login = rs.getString("user_login");
				String password = rs.getString("user_password");
				String role = rs.getString("user_role");
				User user = new User(id, name, lastName, age, email, dob, login, password, role);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warning("no such name and lastName " + userName + " " + userLastName);
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
		return users;
	}


	/*
	 * return User by age range from userAgeFrom to userAgeTo
	 * 
	 * @see dao.AbstractDao#getById(java.lang.Long)
	 */
	@Override
	public List<User> getUserByAgeRange(int userAgeFrom, int userAgeTo) throws SQLException {
		log.info("getting list users by age " + userAgeFrom + " to " + userAgeTo);
		List<User> users = new ArrayList<>();
		connect();
		try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE user_age >= (?) AND user_age <= (?)")) {
			statement.setInt(1, userAgeFrom);
			statement.setInt(2, userAgeTo);
	
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("user_id");
				String name = rs.getString("user_name");
				String lastName = rs.getString("user_LastName");
				int age = rs.getInt("user_age");
				String email = rs.getString("user_email");
				Date dob = rs.getDate("user_dateOfBirth");
				String login = rs.getString("user_login");
				String password = rs.getString("user_password");
				String role = rs.getString("user_role");
				User user = new User(id, name, lastName, age, email, dob, login, password, role);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warning("no such users by age " + userAgeFrom + " to " + userAgeTo);
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
		return users;
	}


	/*
	 * return User by email
	 * 
	 * @see dao.AbstractDao#getById(java.lang.Long)
	 */
	@Override
	public User getUserByEmail(String userEmail) throws SQLException {
		log.info("getting user by email " + userEmail);
		User result = null;
		connect();
		try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE user_email = (?)")) {
			statement.setString(1, userEmail);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				Long id = rs.getLong("user_id");
				String name = rs.getString("user_name");
				String lastName = rs.getString("user_lastName");
				int age = rs.getInt("user_age");
				String email = rs.getString("user_email");
				Date dob = rs.getDate("user_dateOfBirth");
				String login = rs.getString("user_login");
				String password = rs.getString("user_password");
				String role = rs.getString("user_role");
				result = new User(id, name, lastName, age, email, dob, login, password, role);
				rs.close();
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warning("no such email " + userEmail);
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
		return result;
	}


	
		/*
		 * return User by age range from userAgeFrom to userAgeTo
		 * 
		 * @see dao.AbstractDao#getById(java.lang.Long)
		 */
		@Override
		public List<User> getUserByDob(Date userDob) throws SQLException {
			log.info("getting list users by date of birth " + userDob);
			List<User> users = new ArrayList<>();
			connect();
			try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE user_dateOfBirth = (?)")) {
				statement.setDate(1, userDob);
		
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					Long id = rs.getLong("user_id");
					String name = rs.getString("user_name");
					String lastName = rs.getString("user_LastName");
					int age = rs.getInt("user_age");
					String email = rs.getString("user_email");
					Date dob = rs.getDate("user_dateOfBirth");
					String login = rs.getString("user_login");
					String password = rs.getString("user_password");
					String role = rs.getString("user_role");
					User user = new User(id, name, lastName, age, email, dob, login, password, role);
					users.add(user);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				log.warning("no such users by date of birth " + userDob);
			} finally {
				try {
					disconnect();
				} catch (SQLException e) {
					log.warning("can't close connection");
				}
			}
			return users;
		}


	enum UserDatabaseMySQL {
		GET("SELECT * FROM users WHERE user_id = (?)"), 
		REMOVE("DELETE FROM users  WHERE user_id = (?)"), 
		UPDATE("UPDATE users SET user_name = (?), user_lastName = (?), user_age = (?), user_email = (?), user_dateOfBirth = (?), user_login = (?), user_password = (?),"
						+ "user_role = (?), WHERE user_id = (?)"), 
		ADD("INSERT INTO users (user_id, user_name, user_lastName, user_age, user_email, user_dateOfBirth, user_login, user_password, user_role) "
										+ "VALUES (DEFAULT, (?), (?), (?), (?), (?), (?), (?), (?)");

		String QUERY;

		UserDatabaseMySQL(String QUERY) {
			this.QUERY = QUERY;
		}
	}

}
