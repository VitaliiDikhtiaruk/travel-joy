package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import dao.TourDao;
import dao.impl.CountryDatabaseDao.CountryDatabaseMySQL;
import model.Tour;

public class TourDatabaseDao implements TourDao {

	private String jdbcURL = "jdbc:mysql://localhost:3306/travel_agency";
	private String jdbcUsername = "root";
	private String jdbcPassword = "root";
	private Connection connection;

	private static Logger log = Logger.getLogger(TourDatabaseDao.class.getName());

	public TourDatabaseDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
	 * return list of all Tours
	 * @see dao.AbstractDao#getAll()
	 */
	@Override
	public List<Tour> getAll() throws SQLException {
		log.info("getting list of all countries");
		List<Tour> tours = new ArrayList<>();
		connect();
		try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM tours")) {
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("tour_id");
				String name = rs.getString("tour_name");
				String description = rs.getString("tour_description");
				int price = rs.getInt("tour_price");
				Date date = rs.getDate("tour_date");
				String countries = rs.getString("tour_countries");
				Tour tour = new Tour(id, name, description, price, date, countries);
				tours.add(tour);
			}
			rs.close();
			statement.close();

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
		return tours;
	}

	/*
	 * return Tour by id
	 * 
	 * @see dao.AbstractDao#getById(java.lang.Long)
	 */
	@Override
	public Tour getById(Long id) throws SQLException {
		log.info("getting tour by id " + id);
		Tour result = null;
		connect();
		try (PreparedStatement statement = connection.prepareStatement(TourDatabaseMySQL.GET.QUERY)) {
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				String name = rs.getString("tour_name");
				String description = rs.getString("tour_description");
				int price = rs.getInt("tour_price");
				Date date = rs.getDate("tour_date");
				String countries = rs.getString("tour_countries");
				result = new Tour(id, name, description, price, date, countries);
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
	 * adding Tour to Database
	 * 
	 * @see dao.AbstractDao#add(model.Model)
	 */
	@Override
	public void add(Tour tour) throws SQLException {
		log.info("adding tour " + tour.getName());
		connect();

		try (PreparedStatement statement = connection.prepareStatement(CountryDatabaseMySQL.ADD.QUERY)) {
			statement.setString(1, tour.getName());
			statement.setString(2, tour.getDescription());
			statement.setInt(3, tour.getPrice());
			statement.setDate(4, tour.getDate());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			log.warning("can't create new tour" + tour.getName());
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
	}

	/*
	 * update Tour in the Database
	 * 
	 * @see dao.AbstractDao#update(model.Model)
	 */

	@Override
	public void update(Tour tour) throws SQLException {
		log.info("updating tour " + tour.getName());
		connect();

		try (PreparedStatement statement = connection.prepareStatement(CountryDatabaseMySQL.UPDATE.QUERY)) {
			statement.setString(1, tour.getName());
			statement.setString(2, tour.getDescription());
			statement.setInt(3, tour.getPrice());
			statement.setDate(4, tour.getDate());
			statement.setLong(5, tour.getId());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			log.warning("can't update country " + tour.getName());
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
	}

	/*
	 * delete Tour from the Database
	 * 
	 * @see dao.AbstractDao#remove(model.Model)
	 */

	@Override
	public void remove(Tour tour) throws SQLException {
		log.info("removing tour " + tour.getName());
		connect();
		try (PreparedStatement statement = connection.prepareStatement(CountryDatabaseMySQL.REMOVE.QUERY)) {
			statement.setLong(1, tour.getId());
			statement.close();
		} catch (SQLException e) {
			log.warning("can't update tour " + tour.getName());
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}

	}

	/*
	 * return list of tours by country_id
	 */
	@Override
	public List<Tour> getToursByCountry(Long country_id) throws SQLException {
		log.info("getting list of tours by country id " + country_id);
		List<Tour> tours = new ArrayList<>();
		connect();
		try (PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM countries_has_tours WHERE countries_country_id = (?)")) {
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("tours_tour_id");
				tours.add(getById(id));
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			log.warning("can't get list of countries by tour id " + country_id);
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
		return tours;
	}

	/*
	 * return list of tours by price lower then int tourPrice
	 */

	@Override
	public List<Tour> getToursByPriceLowerThen(int tourPrice) throws SQLException {
		log.info("getting list of tours by price lower or equal then " + tourPrice);
		List<Tour> tours = new ArrayList<>();
		connect();
		try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM tours WHERE tour_price <= (?)")) {
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("tour_id");
				String name = rs.getString("tour_name");
				String description = rs.getString("tour_description");
				int price = rs.getInt("tour_price");
				Date date = rs.getDate("tour_date");
				String countries = rs.getString("tour_countries");
				tours.add(new Tour(id, name, description, price, date, countries));
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			log.warning("can't get list of tours by tourPrice lower or equal " + tourPrice);
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
		return tours;
	}
	/*
	 * return list of tours by price bigger then int tourPrice
	 */

	@Override
	public List<Tour> getToursByPriceBiggerThen(int tourPrice) throws SQLException {
		log.info("getting list of tours by price bigger or equal then " + tourPrice);
		List<Tour> tours = new ArrayList<>();
		connect();
		try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM tours WHERE tour_price => (?)")) {
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("tour_id");
				String name = rs.getString("tour_name");
				String description = rs.getString("tour_description");
				int price = rs.getInt("tour_price");
				Date date = rs.getDate("tour_date");
				String countries = rs.getString("tour_countries");
				tours.add(new Tour(id, name, description, price, date, countries));
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			log.warning("can't get list of tours by tourPrice bigger or equal " + tourPrice);
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
		return tours;
	}

	/*
	 * return list of tours by date range
	 */

	@Override
	public List<Tour> getToursByDate(DateFormat tourDateStart, DateFormat tourDateEnd) throws SQLException {
		log.info("getting list of tours by date range " + tourDateStart + " " + tourDateEnd);
		List<Tour> tours = new ArrayList<>();
		connect();
		try (PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM tours WHERE tour_date  BETWEEN (?) AND (?)")) {
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("tour_id");
				String name = rs.getString("tour_name");
				String description = rs.getString("tour_description");
				int price = rs.getInt("tour_price");
				Date date = rs.getDate("tour_date");
				String countries = rs.getString("tour_countries");
				tours.add(new Tour(id, name, description, price, date, countries));
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			log.warning("can't get list of tours by date range " + tourDateStart + " " + tourDateEnd);
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				log.warning("can't close connection");
			}
		}
		return tours;
	}

	enum TourDatabaseMySQL {
		GET("SELECT * FROM tours WHERE tour_id = (?)"), 
		REMOVE("DELETE FROM tours WHERE tour_id = (?)"), 
		UPDATE("UPDATE tours SET tour_name = (?), tour_description = (?), tour_price = (?), tour_date = (?) WHERE tour_id = (?)"), 
		ADD("INSERT INTO tours (tour_id, tour_name, tour_description, tour_price, tour_date) VALUES (DEFAULT, (?), (?), (?), (?)");

		String QUERY;

		TourDatabaseMySQL(String QUERY) {
			this.QUERY = QUERY;
		}
	}

}
