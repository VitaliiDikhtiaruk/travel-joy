package dao;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.List;

import model.Tour;



public interface TourDao extends AbstractDao<Tour>  {

	public List<Tour> getToursByCountry(Long id)  throws SQLException;

	public List<Tour> getToursByPriceLowerThen(int price)  throws SQLException;
	
	public List<Tour> getToursByPriceBiggerThen(int price)  throws SQLException;

	public List<Tour> getToursByDate(DateFormat tourDateStatr, DateFormat tourDateEnd)  throws SQLException;

}
