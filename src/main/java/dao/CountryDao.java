package dao;

import java.sql.SQLException;
import java.util.List;

import model.Country;



public interface CountryDao extends AbstractDao<Country> {

	

	public List<Country> getCountriesByTour(Long id) throws SQLException;

}
