package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import dao.CountryDao;
import model.Country;

public class CountryDatabaseDao implements CountryDao {
	private String jdbcURL = "jdbc:mysql://localhost:3306/travel_agency";
	private String jdbcUsername = "root";
	private String jdbcPassword = "root";
	private Connection connection;

	private static Logger log = Logger.getLogger(CountryDatabaseDao.class.getName());

	public CountryDatabaseDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
	 * return Country List
	 * 
	 * @see dao.AbstractDao#getAll()
	 */
	@Override
	public List<Country> getAll() throws SQLException {
		log.info("getting list of all countries");
		List<Country> countries = new ArrayList<>();
		connect();
		try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM countries")) {
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("country_id");
				String name = rs.getString("country_name");
				String description = rs.getString("country_description");
				Country country = new Country(id, name, description);
				countries.add(country);
			}
			rs.close();
			statement.close();

			return countries;

		} catch (SQLException e) {
			log.warning("can't get list countries from the table");
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
		return countries;
	}

	/*
	 * return Country by id
	 * 
	 * @see dao.AbstractDao#getById(java.lang.Long)
	 */
	@Override
	public Country getById(Long id) throws SQLException {
		log.info("getting country by id " + id);
		Country result = null;
		connect();
		try (PreparedStatement statement = connection.prepareStatement(CountryDatabaseMySQL.GET.QUERY)) {
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				String name = rs.getString("country_name");
				String description = rs.getString("country_description");
				result = new Country(id, name, description);
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
	 * adding Country to Database
	 * 
	 * @see dao.AbstractDao#add(model.Model)
	 */
	@Override
	public void add(Country country) throws SQLException {
		log.info("adding country " + country.getName());
		connect();

		try (PreparedStatement statement = connection.prepareStatement(CountryDatabaseMySQL.ADD.QUERY)) {
			statement.setString(1, country.getName());
			statement.setString(2, country.getDescription());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			log.warning("can't create new country" + country.getName());
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
	}

	/*
	 * update Country in the Database
	 * 
	 * @see dao.AbstractDao#update(model.Model)
	 */

	@Override
	public void update(Country country) throws SQLException {
		log.info("updating country " + country.getName());
		connect();

		try (PreparedStatement statement = connection.prepareStatement(CountryDatabaseMySQL.UPDATE.QUERY)) {
			statement.setString(1, country.getName());
			statement.setString(2, country.getDescription());
			statement.setLong(3, country.getId());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			log.warning("can't update country " + country.getName());
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
	}

	/*
	 * delete Country from the Database
	 * 
	 * @see dao.AbstractDao#remove(model.Model)
	 */

	@Override
	public void remove(Country country) throws SQLException {
		log.info("removing country " + country.getName());
		connect();
		try (PreparedStatement statement = connection.prepareStatement(CountryDatabaseMySQL.REMOVE.QUERY)) {
			statement.setLong(1, country.getId());
			statement.close();
		} catch (SQLException e) {
			log.warning("can't update country " + country.getName());
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}

	}

	/*
	 * return list of countries by Tour_id
	 */
	@Override
	public List<Country> getCountriesByTour(Long tour_id) throws SQLException {
		log.info("getting list of countries by tour id " + tour_id);
		List<Country> countries = new ArrayList<>();
		connect();
		try (PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM countries_has_tours WHERE tours_tour_id = (?)")) {
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("countries_country_id");
				countries.add(getById(id));
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			log.warning("can't get list of countries by tour id " + tour_id);
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
		return countries;
	}

	/*
	 * MySQL queries
	 */

	enum CountryDatabaseMySQL {
		GET("SELECT * FROM countries WHERE country_id = (?)"), REMOVE(
				"DELETE FROM countries WHERE counrtry_id = (?)"), UPDATE(
						"UPDATE countries SET country_name = (?), country_description = (?) WHERE country_id = (?)"), ADD(
								"INSERT INTO countries (country_id, country_name, country_description) VALUES (DEFAULT, (?), (?)");

		String QUERY;

		CountryDatabaseMySQL(String QUERY) {
			this.QUERY = QUERY;
		}
	}

}
