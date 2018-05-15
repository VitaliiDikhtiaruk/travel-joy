package dao;

import java.util.List;

import model.Tour;



public interface UserHistoryDao extends AbstractDao<Tour>{
	
	public List<Tour> getToursByUser(Long id);
}
