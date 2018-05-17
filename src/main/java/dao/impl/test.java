package dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Tour;

public class test {
public static void main(String[] args) throws SQLException {
	TourDatabaseDao tourdao;
	  String jdbcURL = "jdbc:mysql://localhost:3306/travel_agency";
			String jdbcUsername = "root";
			String jdbcPassword = "root";
			
			tourdao = new TourDatabaseDao(jdbcURL, jdbcUsername, jdbcPassword);
	List<Tour> list = new ArrayList();
	list = tourdao.getAll();
	System.out.println(list);
}
}
